    let locas = [];
    mapboxgl.accessToken = MAPBOX_TOKEN;
    var map = new mapboxgl.Map({
    container: 'map',
    style: 'mapbox://styles/mapbox/streets-v11',
    zoom: 10,
    center: [-96.80421034388885, 32.818180319333365]
});

    let locationNames = document.querySelectorAll('.locs')
    let locationLat = document.querySelectorAll('.locsLat')
    let locationLon = document.querySelectorAll('.locsLon')
    let locationDes = document.querySelectorAll('.description')
    let locationId = document.querySelectorAll('.id')
    let locationAddress = document.querySelectorAll('.address')

    for (let i = 0; i < locationNames.length; i++){
    locas[i] = {
        id: locationId[i].getAttribute('value'),
        name: locationNames[i].getAttribute('value'),
        coordinates:[parseFloat(locationLat[i].getAttribute('value')), parseFloat(locationLon[i].getAttribute('value'))],
        description: locationDes[i].getAttribute('value'),
        address: locationAddress[i].getAttribute('value')}

}

    locas.forEach((loca) => {
    let marker = new mapboxgl.Marker({color: "red"})
    .setLngLat(loca.coordinates)
    .addTo(map);

    let popup = new mapboxgl.Popup()
    .setHTML("<h3 style='color: black'>" + loca.name + "</h3>" +
    "<p style='color: black'>" + loca.address + "</p>" +
    "<p style='color: black'>" + loca.description + "</p>");
    marker.setPopup(popup)
});

    const detailsButtons = document.getElementsByClassName("details-button");
    for (let i = 0; i < detailsButtons.length; i++) {
    detailsButtons[i].addEventListener("click", function () {
        const name = this.dataset.name;
        const location = locas.find(loc => loc.name === name);
        openPopup(location);
    });
}

    function openPopup(location) {
    const popupContainer = document.createElement("div");
    popupContainer.className = "popup-container";

    const popup = document.createElement("div");
    popup.className = "popup";
    console.log(location)
    popup.innerHTML = "<h3>" + location.name + "</h3>" +
    "<p>" + location.address + "</p>" +
    "<p>" + location.description + "</p>" +
    "<button class='review-button' onclick='openReviewForm(" + location.id + ")'>Review</button>" +
    "<button class='close-button' onclick='closePopup()'>Close</button>";
    fetch(`https://wagster.site/review/${location.id}`)
    // fetch(`http://localhost:8080/review/${location.id}`)
    .then(resp => resp.json())
    .then(data => {
    data.forEach((review) => {
        popup.innerHTML +=  '<div>' +
            `<img class="profile-pic" src="${review.user.imageURL}">` +
            `<h3 class="username">${review.user.username}</h3>` +
            '<p class="review">' + review.comment + '</p>' +
            '<p class="comment">' + review.rating + '</p>' +
            '<hr>' +
            '</div>'
    })
    console.log(data)
})
    .catch(error => console.error(error))

    popupContainer.appendChild(popup);
    document.body.appendChild(popupContainer);
}

    function openReviewForm(locationId) {
    // Assuming you have a route for the review form, update the URL below accordingly
    const reviewFormUrl = "/review/create?locationId=" + locationId;
    window.location.href = reviewFormUrl;
}


    function closePopup() {
    const popupContainer = document.getElementsByClassName("popup-container")[0];
    popupContainer.remove();
}

    let nav = new mapboxgl.NavigationControl();
    map.addControl(nav, 'top-right');

    const searchInput = document.getElementById('search-bar');
    searchInput.addEventListener('input', function () {
    const query = this.value.toLowerCase();
    const cards = document.getElementsByClassName('card');

    for (let i = 0; i < cards.length; i++) {
    const card = cards[i];
    const name = card.querySelector('h3').textContent.toLowerCase();
    const address = card.querySelector('p').textContent.toLowerCase();

    if (name.includes(query) || address.includes(query)) {
    card.style.display = 'block';
} else {
    card.style.display = 'none';
}
}
});
