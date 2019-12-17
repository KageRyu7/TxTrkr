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
        console.log("Scraping citibank");
        var total = document.getElementById("currentBalanceAmount").textContent;
        var transactionTable = document.getElementById("postedTransactionTable").getElementsByClassName("table-body")[0];
        var rows = transactionTable.querySelectorAll('tr[id^="postedTransactionTableBodyRow-"]');   
        var transactions = [];
        rows.forEach( row => {     
            var transaction = {
                date: getDataFromRow(row,"date"),
                amount: getDataFromRow(row,"amount"),
                balance: getDataFromRow(row,"runningBalance"),
                purchaseDate: getDataFromDescription(row,['[id*="extendedDesPurchaseDateValue"]']),
                postedDate: getDataFromDescription(row,['[id*="extendedDesPostedDateValue"]']),
                purchaseMethod: getDataFromDescription(row,['[id*="extendedDesPurchaseMethodValue"]']),
                type: getDataFromDescription(row,['[id*="extendedDesTransTypeValue"]'],'debit'),
                description: getDataFromDescription(row,['.details-button', '.btn-text'])
            };
            transactions.push(transaction);
        });
        var dataPackage = {type:"Citi", total:total, transactions:transactions};
        postTransactions(dataPackage)
        chrome.runtime.sendMessage({subject:"display", body: dataPackage });
    }
);

function postTransactions(currentData) {
    const req = new XMLHttpRequest();
    const baseUrl = "http://localhost:8000/raw/citiTransactions";
    const urlParams = ``;

    req.open("POST", baseUrl, true);
    req.setRequestHeader("Content-type", "application/json");
    req.send(JSON.stringify(currentData));

    req.onreadystatechange = function() { // Call a function when the state changes.
        if (this.readyState === XMLHttpRequest.DONE && this.status === 200) {
            console.log("Got response 200!");
        }
    }
}