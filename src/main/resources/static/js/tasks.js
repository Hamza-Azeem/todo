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
table.appendChild(tbody);

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
    await complete();
    await update(data);
    await deleteTask();
    await createNewTasks();
}

async function showTasks(tasks) {
    tbody.textContent = '';
    
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
    }
}



await main();

async function complete() {
    const completeBtns = document.querySelectorAll('.btn-success');
    for (let btn of completeBtns) {
        btn.addEventListener(
            'click',
            async function () {
                let finishedTask = {
                    'status': 3
                };
                await updateTask(finishedTask, id, btn.getAttribute('taskId'));
                // table.removeChild(tbody);
                tbody.textContent = '';
                await main();
            }
        );
    }
}

async function update(tasks) {
    const updateBtns = document.querySelectorAll('.btn-warning');
    for (let updateBtn of updateBtns) {
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
                await saveChanges(nameInput, statusSelect, task.id);
            }
        )
    }
}

async function saveChanges(nameInput, statusSelect, taskId) {
    saveChangesBtn.onclick = 
        async function(){
            let updatedTask = {
                'name': nameInput.value,
                'status': statusSelect.value
            }
            await updateTask(updatedTask, id, taskId);
            await main();
        };
}

async function createChanges(nameInput, statusSelect) {
    saveChangesBtn.onclick = 
        async function(){
            let updatedTask = {
                'name': nameInput.value,
                'status': statusSelect.value
            }
            await createTask(id, updatedTask);
            await main();
        };
}

async function deleteTask(){
    const deleteBtns = document.querySelectorAll('.btn-danger');
    for(let deleteBtn of deleteBtns){
        deleteBtn.addEventListener(
            'click',
            async function () {
                await deleteTaskById(id, deleteBtn.getAttribute('taskId'));
                await main();
            }
        )
    }
}

async function createNewTasks() {
    addTaskBtn.onclick = async function () {
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
        await createChanges(nameInput, statusSelect);
    };
}