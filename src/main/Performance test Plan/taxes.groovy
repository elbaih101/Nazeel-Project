import org.apache.jmeter.services.FileServer


def json = new groovy.json.JsonSlurper().parse(prev.getResponseData())
Double totalTaxes = 0.0
Double rate = Double.parseDouble(vars.get("rate"))
boolean inclusive = false
Double rent = 0.0
Double inclusiveTaxes = 0.0
if (json.data.any { tax -> (tax.isActive && tax.appliedForName == "Lodging Fees(Fee)" && tax.taxAppliedForSource == 1) }) {
    rent = (Double.parseDouble(vars.get("rate")) / ((0.15 * 0.025) + 1 + 0.15 + 0.025))
} else if (json.data.any { tax -> (!tax.isActive && tax.appliedForName == "Lodging Fees(Fee)" && tax.taxAppliedForSource == 1) } && json.data.any { tax -> (tax.isActive && tax.taxName == "Lodging Fees") }) {
    rent = (Double.parseDouble(vars.get("rate")) / (1 + 0.15 + 0.025))
}
json.data.each { tax ->
    Double gotTaxes = tax.totalAmount
//method Id --> amount= 2 or percentage = 1
    if (tax.isActive) {
        //Rent Taxes
        if (tax.appliedForName == "Rent" || (tax.appliedForName == "Lodging Fees(Fee)" && tax.taxAppliedForSource == 1)) {
            if (tax.methodId == 1) {
                if (tax.inclusive == false) {
                    if (tax.appliedForName == "Lodging Fees(Fee)") {
                        i++
                        tax.put("totalAmount", Double.parseDouble(vars.get("rate")) * (tax.amount * 0.01 * 0.025))
                    } else if (tax.appliedForName == "Rent") {
                        tax.put("totalAmount", Double.parseDouble(vars.get("rate")) * (tax.amount * 0.01))
                    }


                } else if (tax.inclusive) {

                    if (tax.appliedForName == "Lodging Fees(Fee)") {
                        tax.put("totalAmount", (rent) * (tax.amount * 0.01 * 0.025))
                    } else if (tax.appliedForName == "Rent") {
                        tax.put("totalAmount", (rent) * (tax.amount * 0.01))

                    }
                }
            } else {
                tax.put("totalAmount", tax.amount)
            }

            gotTaxes = tax.totalAmount
            //log.info( "tax is",gotTaxes.toString())
            totalTaxes = gotTaxes + totalTaxes
            if (tax.inclusive == false) {
                rate += gotTaxes
            } else {
                inclusiveTaxes = totalTaxes
            }

        }
    }
}
//log.info("total taxes is",totalTaxes.toString())
vars.put("totalTaxes", totalTaxes.toString())
vars.put("inclusiveTaxes", inclusiveTaxes.toString())
vars.put("net", rate.toString())
def newJson = new groovy.json.JsonBuilder(json.data).toPrettyString()
log.info(newJson)
vars.put("reservationTaxes", newJson)