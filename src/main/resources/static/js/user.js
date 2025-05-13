const urlParams = new URLSearchParams(window.location.search);
const id = urlParams.get('id');
let container = document.querySelector('.container');

async function fetchUserById(id) {
  try {
    let resp = await fetch(`http://localhost:8080/users/${id}`);
    if (!resp.ok) {
      throw new Error("User not found.");
    }
    return await resp.json();
  } catch (error) {
    console.log(error);
  }
}

async function main() {
  let user = await fetchUserById(id);
  let header = document.querySelector("h1");
  header.textContent = `Hello, ${captilize(user.firstName)} ${captilize(user.lastName)}`;
  buildDetails(user);
  let showTasksBtn = document.createElement('button');
  showTasksBtn.classList.add('btn', 'btn-success', 'mt-2');
  showTasksBtn.style.position = 'absolute';
  showTasksBtn.textContent = 'Show Tasks';
  showTasksBtn.style.width = '95%'
  container.appendChild(showTasksBtn);
  showTasksBtn.addEventListener(
    'click',
    function(){
      window.location.href = `tasks.html?userId=${id}`;
    }
  )
}

captilize = (str) => {
  return str[0].toUpperCase() + str.slice(1);
}

main();

function buildDetails(obj){
  
  let ul = document.createElement('ul');
  ul.classList.add('list-group');
  container.appendChild(ul);
  for(key in obj){
    let li = document.createElement('li');
    li.textContent = `${captilize(key)}: ${obj[key]}`;
    li.style.textAlign = 'center';
    li.classList.add('list-group-item');
    ul.appendChild(li);
  }
}