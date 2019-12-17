var currentData = {}

document.addEventListener("DOMContentLoaded", function () {
    //Get Reference to Functions
    backGround = chrome.extension.getBackgroundPage();
    //Call Function
    backGround.onBtnClick();
});


chrome.runtime.onMessage.addListener(
    function(request, sender, sendResponse) {
        if (request.subject === "display") {
            currentData = request.body;
            update();
        }
    }
);

function update() {
    document.getElementById("totalField").textContent = currentData.total;
    document.getElementById("typeField").textContent = currentData.type;
    document.getElementById("postButton").addEventListener("click", postTransactions);

    var transactionTable = document.getElementById("transactionTable");
    var transactiondata = currentData.transactions;
    
    var childRows = "<tr><td>Date</td><td>Amount</td><td>Balance</td><td>Description</td></tr>";
    
    for(i=0;i<transactiondata.length;i++){
        var rowData = transactiondata[i];
        var newRow = "<tr><td>"+rowData.date+"</td><td>"+rowData.amount+"</td><td>"+rowData.balance+"</td><td>"+rowData.description+"</td></tr>"
        childRows += newRow;
    }
    transactionTable.innerHTML = childRows;
}