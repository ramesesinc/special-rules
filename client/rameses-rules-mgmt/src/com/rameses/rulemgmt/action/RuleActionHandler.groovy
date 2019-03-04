package com.rameses.rulemgmt.action;

import com.rameses.rcp.common.*;
import com.rameses.rcp.annotations.*;
import com.rameses.osiris2.client.*;
import com.rameses.osiris2.common.*

public class RuleActionHandler  {

    @Binding
    def binding;

    @Caller
    def caller;

    def rule;
    def action;
    def actionParam;
    def actionParamDef;

    public def getRoot() {
        return caller.getRoot();    
    }
    
    public def getService() {
        return getRoot().getRuleMgmtService();
    }
    
    public def getQuerySvc() {
        return getRoot().getQueryService();
    }
    
    void init() {
    }

    void removeAction() {
        caller.removeParam( actionParam );
    }

}