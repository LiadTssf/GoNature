package data;

public class WorkerAccount extends Account {
    public String   firstname;
    public String   lastname;
    public int      worker_id;
    public String   park_id;
    public String   park_id_fk;
    public String   worker_role;                    // { Worker, Sales, Park_Manager, Department_Manager }

    public WorkerAccount(int account_id_pk, String account_type) {
        super(account_id_pk, account_type);
    }
    public String getPark_id() {
        return park_id;
    }
}
