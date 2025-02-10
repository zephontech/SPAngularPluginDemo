package zephon.entitlement.certification.plugin.rest;

import zephon.entitlement.certification.plugin.entity.ManagedAttribute;
import zephon.entitlement.certification.plugin.service.EntitlementCertificationService;
import java.util.List;
import java.util.Map;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import sailpoint.rest.plugin.AllowAll;
import sailpoint.rest.plugin.BasePluginResource;
import sailpoint.tools.GeneralException;

@Path("entitmements")
@Produces({"application/json"})
@Consumes({"application/json"})
public class EntitlementCertificationController extends BasePluginResource {
    
    private Logger logger = LogManager.getLogger(EntitlementCertificationController.class);
    
    @Override
    public String getPluginName() {
        return "EntitlementCertificationPlugin";
    }
    
   
    @GET
    @Path("getEntitlements")
    @AllowAll
    public List<ManagedAttribute> getEntitlements() throws GeneralException
    {
        logger.debug("*** EntitlementCertificationController getEntitlements ***");
        EntitlementCertificationService service = getService();
        List<ManagedAttribute> attrs = service.getManagedAttributesForUser(getLoggedInUserId());
        logger.debug("ManagedAttribute:" + attrs);
        return attrs;
    }
    
    @GET
    @Path("getEntitlement/{id}")
    @AllowAll
    public ManagedAttribute getEntitlement(@PathParam("id") String id) throws GeneralException
    {
        logger.debug("*** EntitlementCertificationController getEntitlement ***:" + id);
        EntitlementCertificationService service = getService();
        ManagedAttribute attrs = service.getManagedAttribute(id);
        logger.debug("ManagedAttribute:" + attrs);
        return attrs;
    }
    
    @POST
    @Path("certify/{id}")
    @AllowAll
    public void certifyById(@PathParam("id") String id) throws GeneralException
    {
        logger.debug("*** certifyById certify *** " + id);
        EntitlementCertificationService service = getService();
        service.certifyManagedAttribute(id);
    }
    
    @POST
    @Path("reset/{id}")
    @AllowAll
    public void resetById(@PathParam("id") String id) throws GeneralException
    {
        logger.debug("*** EntitlementCertificationController certify *** " + id);
        EntitlementCertificationService service = getService();
        service.resetManagedAttribute(id);
    }
    
    @POST
    @Path("certify")
    @AllowAll
    public ManagedAttribute certify(Map<String, String> data) throws GeneralException
    {
        logger.debug("*** EntitlementCertificationController certify *** " + data);
        EntitlementCertificationService service = getService();
        ManagedAttribute attr = service.getManagedAttribute(data.get("id)"));
        logger.debug("ManagedAttribute:" + attr);
        return attr;
    }
    
    private String getLoggedInUserId() throws GeneralException {
        logger.debug("Logged in User:" + getLoggedInUser().getId());
        //getLoggedInUser().getName();
        String user = getLoggedInUser().getId();
        return user;
    }

    private EntitlementCertificationService getService() {
        return new EntitlementCertificationService(this);
    }
}
