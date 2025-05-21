import axios from "../../api/axios";


export default  function AdminUpdateTask({taskUpdate, onChange}){

    async function handleAdminUpdate(){
        try{
            console.log(taskUpdate);
            const resp = await axios.put("/admin/update-task", taskUpdate);
            onChange();
            console.log(resp.data);
        }catch(error){
            console.log(error.response?.data || error.message);
        }
    }

    return(
         <button className="btn btn-warning" onClick={handleAdminUpdate}>Update</button>
    );
}