export default async function updateTask(taskRequest, userId, taskId) {
    try {
        userId = Number(userId);
        taskId = Number(taskId);
        let resp = await fetch(`http://localhost:8080/tasks/${userId}/${taskId}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(taskRequest)
        })
        if (!resp.ok) {
            throw new Error("UPDATE_REJECTED");
        }
        console.log("UPDATE_SUCCESSFULL")
    }catch(error){
        console.log(error);
    }
}