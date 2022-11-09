header(‘Content-Type: application/javascript‘);
let submitButton = document.getElementById("submit").addEventListener("click", viewDataTable)


async function fetchData(id) {
    const response = await fetch(`https://collectionapi.metmuseum.org/public/collection/v1/objects/${id}`);
    var data = await response.json();
    return data
}


function viewDataTable() {
    let datatable = document.getElementById("datatable")
    let header1 = document.createElement("th");
    header1.innerHTML = "objectId";
    header1.setAttribute("style", "padding:10px" )

    let trEle = document.createElement("tr")
    let header2 = document.createElement("th");
    header2.setAttribute("style", "padding:10px")

    let header3 = document.createElement("th");
    header3.innerHTML = "title";
    header3.setAttribute("style", "padding:10px")

    let header4 = document.createElement("th");
    header4.innerHTML = "culture";
    header4.setAttribute("style", "padding:10px")

    let header5 = document.createElement("th");
    header5.innerHTML = "dynasty";
    header5.setAttribute("style", "padding:10px")

    let header6 = document.createElement("th");
    header6.innerHTML = "artistRole";
    header6.setAttribute("style", "padding:10px")

    let header7 = document.createElement("th");
    header7.innerHTML = "artistNationality";
    header7.setAttribute("style", "padding:10px")

    trEle.append(header1, header2, header3, header4, header5, header6, header7);
    datatable.append(trEle)

    let enteredValue = document.getElementById("input").value;

    let data = fetchData(enteredValue)
    data.then((response) => {
        let ObjectId = document.createElement("td");
        ObjectId.innerHTML = response.objectId;
        ObjectId.setAttribute("style", "padding:10px")

        let ObjectName = document.createElement("td");
        ObjectName.innerHTML = response.objectName;
        ObjectName.setAttribute("style", "padding:10px");

        let Title = document.createElement("td");
        Title.innerHTML = response.title;
        Title.setAttribute("style", "padding:10px");

        let Culture = document.createElement("td");
        Culture.innerHTML = response.culture;
        Culture.setAttribute("style", "padding:10px");

        let Dynasty = document.createElement("td");
        Dynasty.innerHTML = response.dynasty;
        Dynasty.setAttribute("style", "padding:10px");;

        let ArtistRole = document.createElement("td");
        ArtistRole.innerHTML = response.artistRole;
        ArtistRole.setAttribute("style", "padding:10px");

        let ArtistNationality = document.createElement("td");
        ArtistNationality.innerHTML = response.artistNationality;
        ArtistNationality.setAttribute("style", "padding:10px");

        datatable.append(ObjectId, ObjectName, Title, Culture, Dynasty, ArtistRole, ArtistNationality)

    })

}
