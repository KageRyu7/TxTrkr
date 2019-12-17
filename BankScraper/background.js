function onBtnClick() {
    chrome.tabs.query({'active': true, 'currentWindow': true}, (tabs) => {
        for(i=0;i<tabs.length;i++){
            var tab = tabs[i];
            var parseFunction = getScraper(tab.url);
            parseFunction(tab.id);
        }
    });
}

function getScraper(url) {
    if(url.match("https://online.citi.com/.*/accountdetails.*")) {
        return scrape;
    } else {
        return alertMustLogin;
    }
}

function alertMustLogin() {
    alert("You much first login to a citi or chase account.");
}

function scrape(tabId) {
    chrome.tabs.sendMessage(tabId, {"message": "go"});
}