export default async function deleteTaskById(userId, taskId) {
    try{
        const resp = await fetch(`http://localhost:8080/tasks/${userId}/${taskId}`, {
            method: "DELETE"
        });
        if(!resp.ok){
            throw new Error("DELETION_REJECTED");
        }
        console.log("DELETED_SUCCESSFULLY");
    }catch(error){
        console.log(error);
    }
    
}