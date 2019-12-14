chrome.browserAction.onClicked.addListener(function(tab) {
    chrome.tabs.executeScript(null, {file: "testScript.js"});
 });

chrome.tabs.onUpdated.addListener(function (tabId , info) {
    if (info.status === 'complete') {
        // alert("total: "+info);
        chrome.tabs.query({'active': true, 'lastFocusedWindow': true}, function (tabs) {
            var activeTab = tabs[0];
            chrome.tabs.sendMessage(activeTab.id, {"message": "complete"});
        });
    }
});