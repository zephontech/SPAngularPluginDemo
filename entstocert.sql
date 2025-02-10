select 
--ma.*,
usr.id usrid,usr.name usrname,usr.workgroup,ma.id maid,ma.owner maowner,ma.approver_1 maapprover,ma.display_name
from 
identityiq.spt_managed_attribute ma,
identityiq.spt_identity usr
where 
-- id = '0aaead49885f1be78188df052aea05d2';
ma.owner is not null
and usr.workgroup = '0' 
and usr.id = ma.owner
and usr.id = '0aaead48745415c98174790564ce1906'
union all
select 
--ma.*,
usr.id usrid,usr.name usrname,usr.workgroup,ma.id maid,ma.owner maowner,ma.approver_1 maapprover,ma.display_name
from 
identityiq.spt_managed_attribute ma,
identityiq.spt_identity usr,
identityiq.spt_identity_workgroups wg
where 
-- id = '0aaead49885f1be78188df052aea05d2';
ma.owner is not null
and usr.workgroup = '1' 
and usr.id = ma.owner
and usr.id = wg.workgroup
and wg.identity_id = '0aaead48745415c98174790564ce1906';