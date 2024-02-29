package data;

public class WorkerAccount extends Account {
    public String   firstname;
    public String   lastname;
    public int      worker_id;
    public String   park_id_fk;
    public String   worker_role;                    // { Worker, Sales, Park_Manager, Department_Manager }
}
