package com.rameses.rulemgmt.models;

import com.rameses.rcp.common.*;
import com.rameses.rcp.annotations.*;
import com.rameses.osiris2.client.*;
import com.rameses.osiris2.common.*
import com.rameses.osiris2.reports.*;
import com.rameses.seti2.models.*;
import java.rmi.server.UID;
import com.rameses.rulemgmt.developer.*;
         
class FactListModel extends CrudListModel {
    
    public String getTitle() {
        return "Facts " + (connection!=null ? "("+ connection.toUpperCase() + ")" : "");
    }
    
    public def getRoot() {
        return caller?.getRoot();
    }
    
    void init() {
        connection = caller?.getRoot()?.connection;
        super.init();
    }
    
}