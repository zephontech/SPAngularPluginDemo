package zephon.entitlement.certification.plugin.entity;


public class ManagedAttribute {
    
    private String approverId;
    private String approverEmpId;
    private String managedAttributeId;
    private String managedAttributeName;
    private String managedAttributeValue;
    private String applicationName;
    private String requestable;
    private String extended1;
    private String extended2;
    private String extended3;
    private boolean complete;
    
    
    
    public static final String MASQL = 
            "select " +
            "usr.id usrid,usr.name usrname,usr.workgroup,ma.id maid,ma.owner maowner,ma.displayable_name entname,app.name appname,ma.certstatus mastatus " +
            "from  " +
            "identityiq.spt_managed_attribute ma, " +
            "identityiq.spt_application app," +
            "identityiq.spt_identity usr " +
            "where  " +
            "ma.owner is not null " +
            "and usr.workgroup = '0'  " +
            "and usr.id = ma.owner " +
            "and ma.application = app.id " +
            "and usr.id = ? " +
            "union all " +
            "select  " +
            "usr.id usrid,usr.name usrname,usr.workgroup,ma.id maid,ma.owner maowner,ma.displayable_name entname,app.name appname,ma.certstatus mastatus " +
            "from  " +
            "identityiq.spt_managed_attribute ma, " +
            "identityiq.spt_application app," +
            "identityiq.spt_identity usr, " +
            "identityiq.spt_identity_workgroups wg " +
            "where  " +
            "ma.owner is not null " +
            "and usr.workgroup = '1'  " +
            "and usr.id = ma.owner " +
            "and ma.application = app.id " +
            "and usr.id = wg.workgroup " +
            "and wg.identity_id = ?";

    public static final String GETMA = 
            "SELECT ma.*,app.name as appname " + 
            "from " + 
            "identityiq.spt_managed_attribute ma," +
            "identityiq.spt_application app " +
            "where " + 
            "ma.application = app.id " +
            "and ma.id=? ";

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }
    
    public String getApproverId() {
        return approverId;
    }

    public void setApproverId(String approverId) {
        this.approverId = approverId;
    }

    public String getApproverEmpId() {
        return approverEmpId;
    }

    public void setApproverEmpId(String approverEmpId) {
        this.approverEmpId = approverEmpId;
    }

    public String getManagedAttributeId() {
        return managedAttributeId;
    }

    public void setManagedAttributeId(String managedAttributeId) {
        this.managedAttributeId = managedAttributeId;
    }

    public String getManagedAttributeName() {
        return managedAttributeName;
    }

    public void setManagedAttributeName(String managedAttributeName) {
        this.managedAttributeName = managedAttributeName;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getManagedAttributeValue() {
        return managedAttributeValue;
    }

    public void setManagedAttributeValue(String managedAttributeValue) {
        this.managedAttributeValue = managedAttributeValue;
    }

    public String getExtended1() {
        return extended1;
    }

    public void setExtended1(String extended1) {
        this.extended1 = extended1;
    }

    public String getExtended2() {
        return extended2;
    }

    public void setExtended2(String extended2) {
        this.extended2 = extended2;
    }

    public String getExtended3() {
        return extended3;
    }

    public void setExtended3(String extended3) {
        this.extended3 = extended3;
    }

    public String getRequestable() {
        return requestable;
    }

    public void setRequestable(String requestable) {
        this.requestable = requestable;
    }
    
    @Override
    public String toString() {
        return "ManagedAttribute{" + "approverId=" + approverId + ", approverEmpId=" + approverEmpId + ", managedAttributeId=" + managedAttributeId + ", managedAttributeName=" + managedAttributeName + ", managedAttributeValue=" + managedAttributeValue + ", applicationName=" + applicationName + ", extended1=" + extended1 + ", extended2=" + extended2 + ", extended3=" + extended3 + '}';
    }
    
  
  
}
