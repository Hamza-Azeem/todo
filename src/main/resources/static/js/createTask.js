export default async function createTask(userId, taskRequest){
    try{
        let resp = await fetch(`http://localhost:8080/tasks/${userId}`,{
            method:'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(taskRequest)
        });
        console.log("TASK CREATED");
        if(!resp.ok){
            throw new Error("REQUEST_DENIED");
        }
    }catch(error){
        console.log(error);
    }
}