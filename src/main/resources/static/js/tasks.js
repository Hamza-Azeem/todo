import updateTask from '../js/updateTask.js';
import deleteTaskById from '../js/deleteTask.js';
import createTask from '../js/createTask.js';

// variables
const table = document.querySelector('.table');
const urlParams = new URLSearchParams(window.location.search);
const id = urlParams.get('userId');
const myModal = new bootstrap.Modal(document.getElementById('updateModel'));
const modelContent = document.querySelector('.modal-body');
const addTaskBtn = document.querySelector('#addTaskBtn');
let tbody = document.createElement('tbody');
const saveChangesBtn = document.querySelector("#saveChangesBtn");

const taskStatusMapper = {
    1: 'Pending',
    2: 'Started',
    3: 'Completed'
};

// functions 
async function fetchAllUsersTasks(userId) {
    try {
        let resp = await fetch(`http://localhost:8080/tasks/${userId}`);
        if (!resp.ok) {
            throw new Error("Invalid User");
        }
        return await resp.json();
    } catch (error) {
        console.log(error);
    }

}

async function main() {

    let data = await fetchAllUsersTasks(id);
    await showTasks(data);
}

async function showTasks(tasks) {

    table.appendChild(tbody);
    let index = -1;
    for (let task of tasks) {
        index++;
        let tr = document.createElement('tr');
        for (let key in task) {
            let td = document.createElement('td');
            if (key !== 'status') {
                if (key === 'name' && task.status === 3) {
                    td.innerHTML = `<s> ${task.name} </s>`
                } else {
                    td.textContent = task[key];
                }

            } else {
                td.innerText = taskStatusMapper[task[key]];
            }
            tr.appendChild(td);
        }
        // Complete button
        let completeTd = document.createElement('td');
        let complete = document.createElement('button');
        complete.classList.add('btn', 'btn-success');
        complete.setAttribute('taskId', task.id);
        complete.textContent = "Complete";
        completeTd.appendChild(complete);
        tr.appendChild(completeTd);

        // Update button
        let updateTd = document.createElement('td');
        let updateBtn = document.createElement('button');
        updateBtn.classList.add('btn', 'btn-warning');
        updateBtn.setAttribute('taskId', task.id);
        updateBtn.setAttribute('taskIndex', index);
        updateBtn.textContent = "Update";
        updateTd.appendChild(updateBtn);
        tr.appendChild(updateTd);

        // Delete button
        let deleteTd = document.createElement('td');
        let deleteBtn = document.createElement('button');
        deleteBtn.classList.add('btn', 'btn-danger');
        deleteBtn.setAttribute('taskId', task.id);
        deleteBtn.textContent = "Delete";
        deleteTd.appendChild(deleteBtn);
        tr.appendChild(deleteTd);
        tbody.appendChild(tr);

        complete.addEventListener(
            'click',
            async function () {
                let finishedTask = {
                    'status': 3
                };
                await updateTask(finishedTask, id, complete.getAttribute('taskId'));
                table.removeChild(tbody);
                tbody.textContent = '';
                await main();
            }
        )

        updateBtn.addEventListener(
            'click',
            async function () {
                modelContent.textContent = "";
                let task = tasks[updateBtn.getAttribute('taskIndex')];
                // Title input
                let titleDiv = document.createElement('div');
                titleDiv.textContent = 'Title: ';
                let nameInput = document.createElement('input');
                nameInput.classList.add('form-control');
                nameInput.value = task.name;
                titleDiv.appendChild(nameInput);


                // Status input
                let statusDiv = document.createElement('div');
                statusDiv.textContent = 'Status: ';
                let statusSelect = document.createElement('select');
                statusSelect.classList.add('form-select');
                const statusOptions = [
                    { value: 1, label: 'Pending' },
                    { value: 2, label: 'Started' },
                    { value: 3, label: 'Completed' }
                ];
                // Create and append options
                statusOptions.forEach(option => {
                    const opt = document.createElement('option');
                    opt.value = option.value;
                    opt.textContent = option.label;

                    // Preselect the current status
                    if (option.value === task.status) {
                        opt.selected = true;
                    }

                    statusSelect.appendChild(opt);
                });

                statusDiv.appendChild(statusSelect);
                modelContent.appendChild(titleDiv);
                modelContent.appendChild(statusDiv);
                myModal.show();
        
                saveChangesBtn.addEventListener(
                    'click',
                    async function () {
                        let updatedTask = {
                            'name': nameInput.value,
                            'status': statusSelect.value
                        }
                        await updateTask(updatedTask, id, task.id);
                        table.removeChild(tbody);  // raises an error.
                        tbody.innerText = '';
                        await main();
                    }
                )
            }
        )
        deleteBtn.addEventListener(
            'click',
            async function () {
                await deleteTaskById(id, deleteBtn.getAttribute('taskId'));
                table.removeChild(tbody);
                tbody.innerText = '';
                await main();
            }
        )
    }
}

addTaskBtn.addEventListener('click', async function () {
    modelContent.textContent = "";
    // Title input
    let titleDiv = document.createElement('div');
    titleDiv.textContent = 'Title: ';
    let nameInput = document.createElement('input');
    nameInput.classList.add('form-control');
    nameInput.placeholder = "Enter task title"
    titleDiv.appendChild(nameInput);

    // Status input
    let statusDiv = document.createElement('div');
    statusDiv.textContent = 'Status: ';
    let statusSelect = document.createElement('select');
    statusSelect.classList.add('form-select');
    const statusOptions = [
        { value: 1, label: 'Pending' },
        { value: 2, label: 'Started' },
        { value: 3, label: 'Completed' }
    ];
    
    statusOptions.forEach(option => {
        const opt = document.createElement('option');
        opt.value = option.value;
        opt.textContent = option.label;
        statusSelect.appendChild(opt);
    });

    statusDiv.appendChild(statusSelect);
    modelContent.appendChild(titleDiv);
    modelContent.appendChild(statusDiv);
    myModal.show();
    
    // Create a one-time event handler
    async function handleSave() {
        let createdTask = {
            'name': nameInput.value,
            'status': statusSelect.value
        }
        await createTask(id, createdTask);
        table.removeChild(tbody);
        tbody.innerText = '';
        await main();
        // Remove the listener after execution
        saveChangesBtn.removeEventListener('click', handleSave);
    }
    
    // Remove any existing listeners first to ensure no duplicates
    saveChangesBtn.removeEventListener('click', handleSave);
    // Add the new listener
    saveChangesBtn.addEventListener('click', handleSave);
});

await main();
