package com.elytraforce.aUtils.core.command.leaf;

import com.elytraforce.aUtils.core.command.model.ActionHandler;

/**
 * Interface representing something that can be used by a command whether
 * it be an action, a value, or something that splits into more actions and values
 *
 * TODO: usage and permission related sh*t probably in it's own interface
 *
 * Why is it called a leaf? I don't fucking know, i was thinking about springbeans and
 * thought, if those little shits can be called beans i will call my command
 * framework leaf. Fuck you :)
 */
public interface Leaf {

    public ActionHandler getHandle();

    public String getIdentifier();
    public Leaf get();

    public boolean isVariable();
    public int getPosition();
}
