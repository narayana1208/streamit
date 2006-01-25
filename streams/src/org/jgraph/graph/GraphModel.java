/*
 * @(#)GraphModel.java  1.0 1/1/02
 *
 * Copyright (C) 2001 Gaudenz Alder
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 *
 */

package org.jgraph.graph;

import java.util.Iterator;
import java.util.Map;

import javax.swing.event.UndoableEditListener;
import javax.swing.undo.UndoableEdit;

import org.jgraph.event.GraphModelListener;

/**
 * The interface that defines a suitable data model for a JGraph.
 *
 * @version 1.0 1/1/02
 * @author Gaudenz Alder
 */

public interface GraphModel {

    //
    // Roots
    //

    /**
     * Returns the number of roots in the model.  Returns 0 if the
     * model is empty.
     *
     * @return  the number of roots in the model
     */
    int getRootCount();

    /**
     * Returns the root at index <I>index</I> in the model.
     * This should not return null if <i>index</i> is a valid
     * index for the model (that is <i>index</i> >= 0 &&
     * <i>index</i> < getRootCount()).
     *
     * @return  the root of at index <I>index</I>
     */
    Object getRootAt(int index);

    /**
     * Returns the index of <code>root</code> in the model.
     * If root is <code>null</code>, returns -1.
     * @param parent a root in the model, obtained from this data source
     * @return the index of the root in the model, or -1
     *    if the parent is <code>null</code>
     */
    int getIndexOfRoot(Object root);

    /**
     * Returns <code>true</code> if <code>node</code> or one of its
     * ancestors is in the model.
     *
     * @return <code>true</code> if  <code>node</code> is in the model
     */
    boolean contains(Object node);

    /**
     * Returns a <code>Map</code> that represents the properties for
     * the specified cell. This properties have precedence over each
     * view's attributes, regardless of isAttributeStore.
     *
     * @return properties of <code>node</code> as a <code>Map</code>
     */
    Map getAttributes(Object node);

    //
    // Graph Structure
    //

    /**
     * Returns the source of <code>edge</code>. <I>edge</I> must be an object
     * previously obtained from this data source.
     *
     * @return <code>Object</code> that represents the source of <i>edge</i>
     */
    Object getSource(Object edge);

    /**
     * Returns the target of <code>edge</code>. <I>edge</I> must be an object
     * previously obtained from this data source.
     *
     * @return <code>Object</code> that represents the target of <i>edge</i>
     */
    Object getTarget(Object edge);

    /**
     * Returns <code>true</code> if <code>port</code> is a valid source
     * for <code>edge</code>. <I>edge</I> and <I>port</I> must be
     * objects previously obtained from this data source.
     *
     * @return <code>true</code> if <code>port</code> is a valid source
     *                           for <code>edge</code>.
     */
    boolean acceptsSource(Object edge, Object port);

    /**
     * Returns <code>true</code> if <code>port</code> is a valid target
     * for <code>edge</code>. <I>edge</I> and <I>port</I> must be
     * objects previously obtained from this data source.
     *
     * @return <code>true</code> if <code>port</code> is a valid target
     *                           for <code>edge</code>.
     */
    boolean acceptsTarget(Object edge, Object port);

    /**
     * Returns an iterator of the edges connected to <code>port</code>.
     * <I>port</I> must be a object previously obtained from
     * this data source. This method never returns null.
     *
     * @param   port  a port in the graph, obtained from this data source
     * @return  <code>Iterator</code> that represents the connected edges
     */
    Iterator edges(Object port);

    /**
     * Returns <code>true</code> if <code>edge</code> is a valid edge.
     *
     * @return <code>true</code> if <code>edge</code> is a valid edge.
     */
    boolean isEdge(Object edge);

    /**
     * Returns <code>true</code> if <code>port</code> is a valid
     * port, possibly supporting edge connection.
     *
     * @return <code>true</code> if <code>port</code> is a valid port.
     */
    boolean isPort(Object port);


    //
    // Group structure
    //

    /**
     * Returns the parent of <I>child</I> in the model.
     * <I>child</I> must be a node previously obtained from
     * this data source. This returns null if <i>child</i> is
     * a root in the model.
     *
     * @param   child  a node in the graph, obtained from this data source
     * @return  the parent of <I>child</I>
     */
    Object getParent(Object child);

    /**
     * Returns the index of child in parent.
     * If either the parent or child is <code>null</code>, returns -1.
     * @param parent a note in the tree, obtained from this data source
     * @param child the node we are interested in
     * @return the index of the child in the parent, or -1
     *    if either the parent or the child is <code>null</code>
     */
    int getIndexOfChild(Object parent, Object child);

    /**
     * Returns the child of <I>parent</I> at index <I>index</I> in the parent's
     * child array.  <I>parent</I> must be a node previously obtained from
     * this data source. This should not return null if <i>index</i>
     * is a valid index for <i>parent</i> (that is <i>index</i> >= 0 &&
     * <i>index</i> < getChildCount(<i>parent</i>)).
     *
     * @param   parent  a node in the tree, obtained from this data source
     * @return  the child of <I>parent</I> at index <I>index</I>
     */
    Object getChild(Object parent, int index);

    /**
     * Returns the number of children of <I>parent</I>.  Returns 0 if the node
     * is a leaf or if it has no children.  <I>parent</I> must be a node
     * previously obtained from this data source.
     *
     * @param   parent  a node in the tree, obtained from this data source
     * @return  the number of children of the node <I>parent</I>
     */
    int getChildCount(Object parent);

    /**
     * Returns whether the specified node is a leaf node.
     * The way the test is performed depends on the
     * <code>askAllowsChildren</code> setting.
     *
     * @param node the node to check
     * @return true if the node is a leaf node
     */
    boolean isLeaf(Object node);

    //
    // Change Support
    //

    /**
     * Inserts the <code>cells</code> and connections into the model,
     * and passes <code>attributes</code> to the views.
     * Notifies the model- and undo listeners of the change.
     */
    void insert(
                Object[] roots,
                Map attributes,
                ConnectionSet cs,
                ParentMap pm,
                UndoableEdit[] e);

    /**
     * Removes <code>cells</code> from the model. If <code>removeChildren</code>
     * is <code>true</code>, the children are also removed.
     * Notifies the model- and undo listeners of the change.
     */
    void remove(Object[] roots);

    /**
     * Applies the <code>propertyMap</code> and the connection changes to
     * the model. The initial <code>edits</code> that triggered the call
     * are considered to be part of this transaction.
     * Notifies the model- and undo listeners of the change.
     * <strong>Note:</strong> If only <code>edits</code> is non-null, the
     * edits are directly passed to the UndoableEditListeners.
     */
    void edit(
              Map attributes,
              ConnectionSet cs,
              ParentMap pm,
              UndoableEdit[] e);

    /**
     * Returns a map of (cell, clone)-pairs for all <code>cells</code>
     * and their children. Special care should be taken to replace
     * references between cells.
     */
    Map cloneCells(Object[] cells);

    //
    // Layering
    //

    /**
     * Sends <code>cells</code> to back.
     */
    void toBack(Object[] cells);

    /**
     * Brings <code>cells</code> to front.
     */
    void toFront(Object[] cells);

    //
    // Listeners
    //

    /**
     * Adds a listener for the GraphModelEvent posted after the model changes.
     */
    void addGraphModelListener(GraphModelListener l);

    /**
     * Removes a listener previously added with <B>addGraphModelListener()</B>.
     */
    void removeGraphModelListener(GraphModelListener l);

    /**
     * Adds an undo listener for notification of any changes.
     * Undo/Redo operations performed on the <code>UndoableEdit</code>
     * will cause the appropriate ModelEvent to be fired to keep
     * the view(s) in sync with the model.
     */
    void addUndoableEditListener(UndoableEditListener listener);

    /**
     * Removes an undo listener.
     */
    void removeUndoableEditListener(UndoableEditListener listener);

}
