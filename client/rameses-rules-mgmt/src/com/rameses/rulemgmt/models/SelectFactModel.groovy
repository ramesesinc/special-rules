package com.rameses.rulemgmt.models;

import com.rameses.rcp.common.*;
import com.rameses.rcp.annotations.*;
import com.rameses.osiris2.client.*;
import com.rameses.osiris2.common.*
        
        
class SelectFactModel  {

    @Binding
    def binding;

    @Caller
    def caller;

    def savehandler;

    def rule;
    def selectedFact;
    def factList;
    def ruleset;
    def mgmt = false;

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
        factList = service.getFacts([ruleset:rule.ruleset]);
    }

    void manage() {
        mgmt = true;
        rule = [ruleset: caller.query.ruleset ];
        factList = service.getFacts([ruleset:rule.ruleset]);
    }

    def doNext() {
        def m = [fact:selectedFact, rule:rule, savehandler:savehandler];
        def h = "default";
        if(selectedFact.dynamic == 1) h = "dynamic";
        if(selectedFact.handler)  h = selectedFact.handler;
        return InvokerUtil.lookupOpener("rulecondition:"+h+":create", m );
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

    def addFact() {
        return InvokerUtil.lookupOpener( "rulefact:create", [ruleset:rule.ruleset, domain:rule.domain] );
    }

    def editFact() {
        if(!selectedFact) return;
        return InvokerUtil.lookupOpener( "rulefact:edit", [entity:selectedFact] );
    }

    void removeFact() {
        if(!MsgBox.confirm("You are about to remove this fact permanently. Proceed?")) return;
        if(!selectedFact) return;
        devService.removeFact(selectedFact);
        binding.refresh();
    }

    void reloadList() {
        factList = service.getFacts([ruleset:rule.ruleset]);            
        binding.refresh();
    }
}