// vars
export default async function getTaskById(userId, taskId){
    try{
        userId = Number(userId);
        taskId = Number(taskId);
        let resp = await fetch(`http://localhost:8080/tasks/${userId}/${taskId}`);
        if(!resp.ok){
            throw new Error("Task or User not valid");
        }
        return await resp.json();
    }catch(error){
        console.log(error);
    }

}