function getDataFromDescription(row, lookFor) {
    return getDataFromDescription(row, lookFor, "null")
}
function getDataFromDescription(row, lookFor, defaultValue) {
    var description = row.querySelector('[id^="'+row.id+'-description"]');
    
    var i;
    var result;
    
    result = description;
    for(i=0;i<lookFor.length;i++){
        result = result.querySelector(lookFor[i]);
    }
    if (result != null) {
        return result.textContent;
    }else{
        return defaultValue;
    }
}

function getDataFromRow (row,field) {
    var dateTD = row.querySelector('[id*="'+field+'"]');
    return $("span:first-child", dateTD)[0].textContent;
}

chrome.runtime.onMessage.addListener(
    function(request, sender, sendResponse) {
        var total = document.getElementById("currentBalanceAmount").textContent;
        console.log(total);
        var transactionTable = document.getElementById("postedTransactionTable").getElementsByClassName("table-body")[0];
        var rows = transactionTable.querySelectorAll('tr[id^="postedTransactionTableBodyRow-"]');   
        var results = [];
        rows.forEach( row => {     
            var transaction = {
                "date:": getDataFromRow(row,"date"),
                "amount": getDataFromRow(row,"amount"),
                "balance": getDataFromRow(row,"runningBalance"),
                "purchaseDate": getDataFromDescription(row,['[id*="extendedDesPurchaseDateValue"]']),
                "postedDate": getDataFromDescription(row,['[id*="extendedDesPostedDateValue"]']),
                "purchaseMethod": getDataFromDescription(row,['[id*="extendedDesPurchaseMethodValue"]']),
                "type": getDataFromDescription(row,['[id*="extendedDesTransTypeValue"]'],'debit'),
                "description": getDataFromDescription(row,['.details-button', '.btn-text'])
            };
            results.push(transaction);
        });

        console.log(results);
    }
);

chrome.browserAction.onClicked.addListener(function(tab) { alert('icon clicked')});