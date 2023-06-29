const client = filestack.init(FILESTACK_TOKEN);
const urlInput = document.querySelector("#url")
console.log(urlInput)

const fileButton = document.getElementById("file")
fileButton.addEventListener("click", (e) => {
    e.preventDefault();

    const pickerOptions = {
        onFileUploadFinished: (file) => {
            console.log(file.url)
            urlInput.setAttribute("value", file.url)
        }
    }
    client.picker(pickerOptions).open();

});