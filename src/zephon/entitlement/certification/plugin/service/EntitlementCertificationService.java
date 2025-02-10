package zephon.entitlement.certification.plugin.service;

import zephon.entitlement.certification.plugin.entity.ManagedAttribute;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import sailpoint.api.SailPointContext;
import sailpoint.api.SailPointFactory;
import sailpoint.plugin.PluginBaseHelper;
import sailpoint.plugin.PluginContext;
import sailpoint.tools.GeneralException;
import sailpoint.tools.IOUtil;


public class EntitlementCertificationService {
    
    private final PluginContext pluginContext;
    
    private Logger logger = LogManager.getLogger(EntitlementCertificationService.class);
    
    public EntitlementCertificationService(PluginContext pluginContext) {
        this.pluginContext = pluginContext;
    }

    
    public List<ManagedAttribute> getManagedAttributesForUser(String userId) throws GeneralException {
        logger.debug("*** getManagedAttributesForUser ***");
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            SailPointContext context = SailPointFactory.getCurrentContext();
            connection = context.getJdbcConnection();
            statement = PluginBaseHelper.prepareStatement(connection, ManagedAttribute.MASQL,userId,userId);
            ResultSet resultSet = statement.executeQuery();
            List<ManagedAttribute> managed = new ArrayList<>();
            while (resultSet.next())
                managed.add(buildManagedAttribute(resultSet));
            return managed;
        } catch (SQLException e) {
            throw new GeneralException(e);
        } finally {
            IOUtil.closeQuietly(statement);
            IOUtil.closeQuietly(connection);
        }
    }
    
    public ManagedAttribute getManagedAttribute(String maid) throws GeneralException {
        logger.debug("*** getManagedAttribute ***:" + maid);
        Connection connection = null;
        PreparedStatement statement = null;
        ManagedAttribute ma = null;
        try {
            SailPointContext context = SailPointFactory.getCurrentContext();
            connection = context.getJdbcConnection();
            statement = PluginBaseHelper.prepareStatement(connection, ManagedAttribute.GETMA,maid);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next())
            {
                ma = buildManagedAttributeView(resultSet);
            }
        } catch (SQLException e) {
            throw new GeneralException(e);
        } finally {
            IOUtil.closeQuietly(statement);
            IOUtil.closeQuietly(connection);
        }
        return ma;
    }
    
    public void certifyManagedAttribute(String maid) throws GeneralException {
        logger.debug("*** certifyManagedAttribute ***:" + maid);
        SailPointContext context = SailPointFactory.getCurrentContext();
        try
        {
            sailpoint.object.ManagedAttribute spma = context.getObjectById(sailpoint.object.ManagedAttribute.class, maid);
            spma.setAttribute("certstatus","certified");
            context.saveObject(spma);
            context.commitTransaction();
        }
        catch(Exception e)
        {
            throw new GeneralException(e);
        }
        

    }
    
    public void resetManagedAttribute(String maid) throws GeneralException {
        logger.debug("*** certifyManagedAttribute ***:" + maid);
        SailPointContext context = SailPointFactory.getCurrentContext();
        try
        {
            sailpoint.object.ManagedAttribute spma = context.getObjectById(sailpoint.object.ManagedAttribute.class, maid);
            spma.setAttribute("certstatus","certify");
            context.saveObject(spma);
            context.commitTransaction();
        }
        catch(Exception e)
        {
            throw new GeneralException(e);
        }
        

    }
    
    private ManagedAttribute buildManagedAttribute(ResultSet rs) throws SQLException
    {
        //usr.id usrid,usr.name usrname,usr.workgroup,ma.id maid,ma.owner maowner,ma.approver_1 maapprover,ma.display_name
        ManagedAttribute ma = new ManagedAttribute();
        ma.setManagedAttributeId(rs.getString("maid"));
        ma.setApproverId(rs.getString("usrid"));
        ma.setApproverEmpId(rs.getString("usrname"));
        ma.setManagedAttributeId(rs.getString("maid"));
        ma.setManagedAttributeName(rs.getString("entname"));
        ma.setApplicationName(rs.getString("appname"));
        String status = rs.getString("mastatus");
        ma.setComplete(false);
        if ("certified".equalsIgnoreCase(status))
        {
            ma.setComplete(true);
        }
        return ma;
    }
    
    private ManagedAttribute buildManagedAttributeView(ResultSet rs) throws SQLException
    {
        //usr.id usrid,usr.name usrname,usr.workgroup,ma.id maid,ma.owner maowner,ma.approver_1 maapprover,ma.display_name
        ManagedAttribute ma = new ManagedAttribute();
        ma.setManagedAttributeId(rs.getString("id"));
        ma.setManagedAttributeName(rs.getString("displayable_name"));
        ma.setApplicationName(rs.getString("appname"));
        ma.setRequestable(rs.getString("requestable"));
        ma.setManagedAttributeValue(rs.getString("value"));
        ma.setExtended1(rs.getString("extended1"));
        return ma;
    }
}
