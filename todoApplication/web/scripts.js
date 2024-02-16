
function fetchTasks() {
    fetch('ViewTaskServlet')
            .then(response => response.text())
            .then(data => {
                document.getElementById('tasksContainer').innerHTML = data;
            })
            .catch(error => console.error('Error fetching tasks:', error));
}
