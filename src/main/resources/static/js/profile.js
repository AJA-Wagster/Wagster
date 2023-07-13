function toggleSettingsMenu() {
    let settingsMenu = document.getElementById("settingsMenu");
    let settingsToggle = document.getElementById("settingsToggle");

    if (settingsMenu.style.display === "none") {
    settingsMenu.style.display = "block";
    settingsToggle.style.transform = "rotate(45deg)";
} else {
    settingsMenu.style.display = "none";
    settingsToggle.style.transform = "rotate(0deg)";
}
}

    function showPosts() {
    document.getElementById("postsContainer").style.display = "block";
    document.getElementById("eventsContainer").style.display = "none";
}

    function showEvents() {
    document.getElementById("postsContainer").style.display = "none";
    document.getElementById("eventsContainer").style.display = "block";
}

    function showAll() {
    document.getElementById("postsContainer").style.display = "block";
    document.getElementById("eventsContainer").style.display = "block";
}

    function toggleEditDeleteButtons(id) {
    const buttons = document.getElementById(id);
    if (window.getComputedStyle(buttons).display === 'none') {
    buttons.style.display = 'flex';
} else {
    buttons.style.display = 'none';
}
}