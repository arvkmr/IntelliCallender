package intellical.kross.com.intellicalender;

/**
 * Created by Arv on 03-11-2014.
 *  This class is used to hold all the task data
 */
public class Row {

    public final static String KEY_ROWID = "_id";
    public final static String KEY_NAME = "name";
    public final static String KEY_BOOL = "bool";
    public final static String KEY_START_TIME = "stime";
    public final static String KEY_END_TIME = "etime";
    public final static String KEY_DESC = "desc";
    public final static String KEY_LOC = "loc";
    public final static String KEY_STATUS = "status";
    public final static long TASK_PENDING = 0;
    public final static long TASK_IN_PROGRESS = 1;
    public final static long TASK_COMPLETED = 2;
    public final static long TASK_INCOMPLETE = 3;
    
    
    long _Id;
    String name;
    String desc;
    String loc;
    long sTime;
    long eTime;
    boolean mReschedule;
    long lStatus;
}


