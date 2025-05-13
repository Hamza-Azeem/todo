async function fetchUsers(){
    try{
        let resp = await fetch("http://localhost:8080/users");
        if(!resp.ok){
            throw new Error("Invalid request");
        }
        return await resp.json();
    }catch(error){
        console.log(error);
    }
}

async function main(){
    let users = await fetchUsers();
    console.log(users);
    buildUsers(users);
}

main();

function buildUsers(arr){
    let ul = document.querySelector('ul');
    for(user of arr){
        let li = document.createElement('li');
        li.classList.add('list-group-item');
        li.style.display = 'flex';
        li.style.justifyContent = 'space-between';
        li.textContent = `User: ${user.email}`;
        let btn = document.createElement('button');
        btn.classList.add('btn', 'btn-primary');
        btn.setAttribute('id', user.id);
        btn.textContent = "show profile";
        li.appendChild(btn);
        ul.appendChild(li);
        btn.addEventListener(
            'click',
            function(){
                window.location.href = `user.html?id=${btn.getAttribute('id')}`;
            }
        )
    }
}
