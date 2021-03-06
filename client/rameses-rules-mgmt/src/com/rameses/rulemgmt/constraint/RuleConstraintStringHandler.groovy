package com.rameses.rulemgmt.constraint;

import com.rameses.rcp.common.*;
import com.rameses.rcp.annotations.*;
import com.rameses.osiris2.client.*;
import com.rameses.osiris2.common.*

public class RuleConstraintStringHandler extends RuleConstraintHandler {

    def varList;

    void init() {
        if(constraint.usevar == 1 ) buildVarList();
    }

    void buildVarList() {
        varList = service.findAllVarsByType( [ruleid:condition.parentid, datatype:field.vardatatype, pos: condition.pos ] ).collect{  
            [objid: it.objid, name: it.name]
        };
    }

    @PropertyChangeListener
    def listener = [
        "constraint.usevar": { o->
            if( o == 1) {
                constraint.stringvalue = null;
                buildVarList();
            }
            else {
                constraint.var = null;
            }
        }
    ]

    def operatorList = [
        [caption:"equal to", symbol:"=="],
        [caption:"not equal to", symbol:"!="],
        [caption:"matches", symbol:"matches"],
    ];

    
}