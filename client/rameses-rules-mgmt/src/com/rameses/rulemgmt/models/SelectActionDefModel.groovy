package com.rameses.rulemgmt.models;

import com.rameses.rcp.common.*;
import com.rameses.rcp.annotations.*;
import com.rameses.osiris2.client.*;
import com.rameses.osiris2.common.*;
        
class SelectActionDefModel  {

    @Caller
    def caller;

    @Binding
    def binding;

    def rule;
    def selectedActionDef;
    def actionDefList;
    def savehandler;
    def mgmt = false;
    def ruleset;
    
    public def getRoot() {
        return caller.getRoot();
    }
    
    public def getDevService() {
        return root.getRuleDeveloperService();
    }

    public def getService() {
        return root.getRuleMgmtService();
    }  

    void init() {
        mgmt = false;
        actionDefList = service.getActionDefs([ruleset:rule.ruleset]);
    }

    void manage() {
        mgmt = true;
        rule = [ruleset: caller.query.ruleset ];
        actionDefList = service.getActionDefs([ruleset:rule.ruleset]);
    }


    def doNext() {
        def m = [actionDef:selectedActionDef, rule:rule, savehandler: savehandler];
        def h = "default";
        if(selectedActionDef.handler)  h = selectedActionDef.handler;
        return InvokerUtil.lookupOpener("ruleaction:"+h+":create", m );
    }

    def doClose() {
        return "_close";
    }

     /******************************************************************
    * debugging
    *******************************************************************/
    boolean getDebug() {
        def d =  OsirisContext.clientContext.appEnv['app.debug'];
        if(!d) return false;
        return d;
    }

    def addActionDef() {
        return InvokerUtil.lookupOpener( "ruleactiondef:create", [ruleset:rule.ruleset, domain:rule.domain] );
    }

    def editActionDef() {
        if(!selectedActionDef) return;
        return InvokerUtil.lookupOpener( "ruleactiondef:edit", [entity:selectedActionDef] );
    }

    void removeActionDef() {
        if(!MsgBox.confirm("You are about to remove this actiondef permanently. Proceed?")) return;
        if(!selectedActionDef) return;
        devService.removeActionDef(selectedActionDef);
        binding.refresh();
    }

    void reloadList() {
        actionDefList = service.getActionDefs([ruleset:rule.ruleset]);
        binding.refresh();
    }

}