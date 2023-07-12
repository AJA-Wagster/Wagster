    function showConfirmation(event) {
    event.preventDefault(); // Prevent form submission

    const confirmationDialog = document.querySelector('.confirmation-dialog');
    const confirmButton = confirmationDialog.querySelector('.confirm-button');
    const cancelButton = confirmationDialog.querySelector('.cancel-button');

    confirmButton.addEventListener('click', function() {
    confirmationDialog.style.display = 'none';
    document.querySelector('#delete-form').submit();
});

    cancelButton.addEventListener('click', function() {
    confirmationDialog.style.display = 'none';
});

    confirmationDialog.style.display = 'block';
}

    function highlightPaws(rating) {
    const pawIcons = document.querySelectorAll('.fa-paw');
    pawIcons.forEach((pawIcon, index) => {
    if (index < rating) {
    pawIcon.classList.add('highlight');
} else {
    pawIcon.classList.remove('highlight');
}
});
}