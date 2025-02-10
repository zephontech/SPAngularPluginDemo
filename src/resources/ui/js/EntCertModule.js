var entCertModule = angular.module('EntCertJSApp', ['ui.bootstrap', 'sailpoint.i18n']);

entCertModule.config(function ($httpProvider) {
    $httpProvider.defaults.xsrfCookieName = "CSRF-TOKEN";
});

/**
 * Controller for the history list.
 */
entCertModule.controller('EntCertJSController', function(entCertJSService,$q,$uibModal) {

    var me = this,
            promises;
    /**
     * @property Array and single record.
     */
    me.mydata = [];
    me.managedattrdata = '';

   
    /**
     * Fetches the MAs from the server.
     *
     * @return Promise A promise that resolves with the array of MAs.
     */
    function fetchEntitlements() {
        alert("fetchEntitlements ctrl");
        return entCertJSService.getEntitlements();
    }
    
    function fetchEntitlement(id) {
        //alert("fetch ent:" + id);
        return entCertJSService.getEntitlement(id);
    }

    /**
     * Gets the ManagedAttributes from the server and sets them on the controller.
     */
    me.getEntitlements = function() {
        //alert("getEntitlements");
        fetchEntitlements().then(function(objects) {
            //alert(objects);
            me.mydata = objects;
        });
    };
    
    me.getEntitlement = function(id) {
        //alert("getEntitlement:" + id);
        fetchEntitlement(id).then(function(objects) {
            //alert('objects:' + objects);
            me.managedattrdata = objects;
        });
    };
    
    me.viewEntitlement = function(mydata) {
        $uibModal.open({
            animation: false,
            controller: 'ViewEntitlementCtrl as ctrl',
            templateUrl: PluginHelper.getPluginFileUrl('EntitlementCertificationPlugin', 'ui/html/modal-template.html'),
            resolve: {
                mydata: function() {
                    return mydata;
                }
            }
        });
    };
    
    me.certify = function(id) {
        //alert("certify:" + id);
        entCertJSService.certify(id).then(me.getEntitlements);
    };
    
    me.reset = function(id) {
        //alert("certify:" + id);
        entCertJSService.reset(id).then(me.getEntitlements);
    };
    
    
    promises = {
        mydata: fetchEntitlements()
    };

    // load the page config and the history
    $q.all(promises).then(function(result) {
       alert('call all promises');
       me.mydata = result.mydata;
    });

});

entCertModule.controller('ViewEntitlementCtrl', function(entCertJSService,$uibModalInstance,mydata) {

    var me = this;
    init();

    /**
     * @property string The notes to display.
     */
    me.mydata = mydata;
    
    function fetchEntitlement(id) {
        //alert("fetch ent:" + id);
        return entCertJSService.getEntitlement(id);
    }
    
    function init () {
        //alert('initid:' + mydata.managedAttributeId);
        fetchEntitlement(mydata.managedAttributeId).then(function(objects) {
            //alert('objects:' + objects);
            me.mydata = objects;
        });
        
      }

    /**
     * Closes the dialog.
     */
    me.close = function() {
        $uibModalInstance.close();
    };

});

/**
 * Service that handles functionality around todos.
 */
entCertModule.service('entCertJSService', function($http) {
    
    var config = {
        headers: {
            'X-XSRF-TOKEN': PluginHelper.getCsrfToken()
        }
    };

    return {
        
        /**
         * the url is what ties the java rest service to this angular module.
         * the names used in this module although seem the same as the java classes
         * it has nothing to do with how it runs.
         */
        getEntitlements: function() {
            alert("calling getEntitlements url");
            var ENCERT_URL = PluginHelper.getPluginRestUrl('entitmements/getEntitlements');
            //alert("getting urls:" + ENCERT_URL);
            return $http.get(ENCERT_URL).then(function(response) {
                return response.data;
                
            });
        },
        getEntitlement: function(id) {
            //alert("getting getEntitlement:" + id);
            var ENCERT_URL = PluginHelper.getPluginRestUrl('entitmements/getEntitlement/' + id);
            //alert("getting url:" + ENCERT_URL);
            return $http.get(ENCERT_URL).then(function(response) {
                return response.data;
                
            });
        },
        certify: function(id) {
            var ENCERT_URL = PluginHelper.getPluginRestUrl('entitmements/certify/' + id);
            return $http.post(ENCERT_URL, null, config).then(function(result) {
                return {};
            });
        },
        reset: function(id) {
            var ENCERT_URL = PluginHelper.getPluginRestUrl('entitmements/reset/' + id);
            return $http.post(ENCERT_URL, null, config).then(function(result) {
                return {};
            });
        }
        
        
    };
});
