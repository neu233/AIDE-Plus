/*******************************************************************************
 * Copyright (c) 2000, 2013 IBM Corporation and others.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/

package org.eclipse.jdt.core.dom;

import java.util.ArrayList;
import java.util.List;

/**
 * Local variable declaration statement AST node type.
 * <p>
 * This kind of node collects several variable declaration fragments
 * (<code>VariableDeclarationFragment</code>) into a statement
 * (<code>Statement</code>), all sharing the same modifiers and base type.
 * </p>
 * <pre>
 * VariableDeclarationStatement:
 *    { ExtendedModifier } Type VariableDeclarationFragment
 *        { <b>,</b> VariableDeclarationFragment } <b>;</b>
 * </pre>
 * <p>
 * Note: This type of node is a convenience of sorts.
 * An equivalent way to represent the same statement is to use
 * a <code>VariableDeclarationExpression</code>
 * wrapped in an <code>ExpressionStatement</code>.
 * </p>
 *
 * @since 2.0
 * @noinstantiate This class is not intended to be instantiated by clients.
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class VariableDeclarationStatement extends Statement {

	/**
	 * The "modifiers" structural property of this node type (type: {@link Integer}) (JLS2 API only).
	 * @since 3.0
	 * @deprecated In the JLS3 API, this property is replaced by {@link #MODIFIERS2_PROPERTY}.
	 */
	public static final SimplePropertyDescriptor MODIFIERS_PROPERTY =
		new SimplePropertyDescriptor(VariableDeclarationStatement.class, "modifiers", int.class, MANDATORY); //$NON-NLS-1$

	/**
	 * The "modifiers" structural property of this node type (element type: {@link IExtendedModifier}) (added in JLS3 API).
	 * @since 3.1
	 */
	public static final ChildListPropertyDescriptor MODIFIERS2_PROPERTY =
		new ChildListPropertyDescriptor(VariableDeclarationStatement.class, "modifiers", IExtendedModifier.class, CYCLE_RISK); //$NON-NLS-1$

	/**
	 * The "type" structural property of this node type (child type: {@link Type}).
	 * @since 3.0
	 */
	public static final ChildPropertyDescriptor TYPE_PROPERTY =
		new ChildPropertyDescriptor(VariableDeclarationStatement.class, "type", Type.class, MANDATORY, NO_CYCLE_RISK); //$NON-NLS-1$

	/**
	 * The "fragments" structural property of this node type (element type: {@link VariableDeclarationFragment}).
	 * @since 3.0
	 */
	public static final ChildListPropertyDescriptor FRAGMENTS_PROPERTY =
		new ChildListPropertyDescriptor(VariableDeclarationStatement.class, "fragments", VariableDeclarationFragment.class, CYCLE_RISK); //$NON-NLS-1$

	/**
	 * A list of property descriptors (element type:
	 * {@link StructuralPropertyDescriptor}),
	 * or null if uninitialized.
	 * @since 3.0
	 */
	private static final List PROPERTY_DESCRIPTORS_2_0;

	/**
	 * A list of property descriptors (element type:
	 * {@link StructuralPropertyDescriptor}),
	 * or null if uninitialized.
	 * @since 3.1
	 */
	private static final List PROPERTY_DESCRIPTORS_3_0;

	static {
		List propertyList = new ArrayList(4);
		createPropertyList(VariableDeclarationStatement.class, propertyList);
		addProperty(MODIFIERS_PROPERTY, propertyList);
		addProperty(TYPE_PROPERTY, propertyList);
		addProperty(FRAGMENTS_PROPERTY, propertyList);
		PROPERTY_DESCRIPTORS_2_0 = reapPropertyList(propertyList);

		propertyList = new ArrayList(4);
		createPropertyList(VariableDeclarationStatement.class, propertyList);
		addProperty(MODIFIERS2_PROPERTY, propertyList);
		addProperty(TYPE_PROPERTY, propertyList);
		addProperty(FRAGMENTS_PROPERTY, propertyList);
		PROPERTY_DESCRIPTORS_3_0 = reapPropertyList(propertyList);
	}

	/**
	 * Returns a list of structural property descriptors for this node type.
	 * Clients must not modify the result.
	 *
	 * @param apiLevel the API level; one of the
	 * <code>AST.JLS*</code> constants

	 * @return a list of property descriptors (element type:
	 * {@link StructuralPropertyDescriptor})
	 * @since 3.0
	 */
	public static List propertyDescriptors(int apiLevel) {
		if (apiLevel == AST.JLS2_INTERNAL) {
			return PROPERTY_DESCRIPTORS_2_0;
		} else {
			return PROPERTY_DESCRIPTORS_3_0;
		}
	}

	/**
	 * The extended modifiers (element type: {@link IExtendedModifier}).
	 * Null in JLS2. Added in JLS3; defaults to an empty list
	 * (see constructor).
	 * @since 3.1
	 */
	private NodeList modifiers = null;

	/**
	 * The modifier flagss; bit-wise or of Modifier flags.
	 * Defaults to none. Not used in JLS3.
	 */
	private int modifierFlags = Modifier.NONE;

	/**
	 * The base type; lazily initialized; defaults to an unspecified,
	 * legal type.
	 */
	private volatile Type baseType;

	/**
	 * The list of variable variable declaration fragments (element type:
	 * {@link VariableDeclarationFragment}).  Defaults to an empty list.
	 */
	private final NodeList variableDeclarationFragments =
		new NodeList(FRAGMENTS_PROPERTY);

	/**
	 * Creates a new unparented local variable declaration statement node owned
	 * by the given AST.  By default, the variable declaration has: no modifiers,
	 * an unspecified (but legal) type, and an empty list of variable
	 * declaration fragments (which is syntactically illegal).
	 * <p>
	 * N.B. This constructor is package-private.
	 * </p>
	 *
	 * @param ast the AST that is to own this node
	 */
	VariableDeclarationStatement(AST ast) {
		super(ast);
		if (ast.apiLevel >= AST.JLS3_INTERNAL) {
			this.modifiers = new NodeList(MODIFIERS2_PROPERTY);
		}
	}

	@Override
	final List internalStructuralPropertiesForType(int apiLevel) {
		return propertyDescriptors(apiLevel);
	}

	@Override
	final int internalGetSetIntProperty(SimplePropertyDescriptor property, boolean get, int value) {
		if (property == MODIFIERS_PROPERTY) {
			if (get) {
				return getModifiers();
			} else {
				setModifiers(value);
				return 0;
			}
		}
		// allow default implementation to flag the error
		return super.internalGetSetIntProperty(property, get, value);
	}

	@Override
	final ASTNode internalGetSetChildProperty(ChildPropertyDescriptor property, boolean get, ASTNode child) {
		if (property == TYPE_PROPERTY) {
			if (get) {
				return getType();
			} else {
				setType((Type) child);
				return null;
			}
		}
		// allow default implementation to flag the error
		return super.internalGetSetChildProperty(property, get, child);
	}

	@Override
	final List internalGetChildListProperty(ChildListPropertyDescriptor property) {
		if (property == MODIFIERS2_PROPERTY) {
			return modifiers();
		}
		if (property == FRAGMENTS_PROPERTY) {
			return fragments();
		}
		// allow default implementation to flag the error
		return super.internalGetChildListProperty(property);
	}

	@Override
	final int getNodeType0() {
		return VARIABLE_DECLARATION_STATEMENT;
	}

	@Override
	ASTNode clone0(AST target) {
		VariableDeclarationStatement result =
			new VariableDeclarationStatement(target);
		result.setSourceRange(getStartPosition(), getLength());
		result.copyLeadingComment(this);
		if (this.ast.apiLevel == AST.JLS2_INTERNAL) {
			result.setModifiers(getModifiers());
		}
		if (this.ast.apiLevel >= AST.JLS3_INTERNAL) {
			result.modifiers().addAll(ASTNode.copySubtrees(target, modifiers()));
		}
		result.setType((Type) getType().clone(target));
		result.fragments().addAll(
			ASTNode.copySubtrees(target, fragments()));
		return result;
	}

	@Override
	final boolean subtreeMatch0(ASTMatcher matcher, Object other) {
		// dispatch to correct overloaded match method
		return matcher.match(this, other);
	}

	@Override
	void accept0(ASTVisitor visitor) {
		boolean visitChildren = visitor.visit(this);
		if (visitChildren) {
			// visit children in normal left to right reading order
			if (this.ast.apiLevel >= AST.JLS3_INTERNAL) {
				acceptChildren(visitor, this.modifiers);
			}
			acceptChild(visitor, getType());
			acceptChildren(visitor, this.variableDeclarationFragments);
		}
		visitor.endVisit(this);
	}

	/**
	 * Returns the live ordered list of modifiers and annotations
	 * of this declaration (added in JLS3 API).
	 * <p>
	 * Note that the final modifier is the only meaningful modifier for local
	 * variable declarations.
	 * </p>
	 *
	 * @return the live list of modifiers and annotations
	 *    (element type: {@link IExtendedModifier})
	 * @exception UnsupportedOperationException if this operation is used in
	 * a JLS2 AST
	 * @since 3.1
	 */
	public List modifiers() {
		// more efficient than just calling unsupportedIn2() to check
		if (this.modifiers == null) {
			unsupportedIn2();
		}
		return this.modifiers;
	}

	/**
	 * Returns the modifiers explicitly specified on this declaration.
	 * <p>
	 * In the JLS3 API, this method is a convenience method that
	 * computes these flags from <code>modifiers()</code>.
	 * </p>
	 *
	 * @return the bit-wise or of <code>Modifier</code> constants
	 * @see Modifier
	 */
	public int getModifiers() {
		// more efficient than checking getAST().API_LEVEL
		if (this.modifiers == null) {
			// JLS2 behavior - bona fide property
			return this.modifierFlags;
		} else {
			// JLS3 behavior - convenience method
			// performance could be improved by caching computed flags
			// but this would require tracking changes to this.modifiers
			int computedModifierFlags = Modifier.NONE;
			for (Object x : modifiers()) {
				if (x instanceof Modifier) {
					computedModifierFlags |= ((Modifier) x).getKeyword().toFlagValue();
				}
			}
			return computedModifierFlags;
		}
	}

	/**
	 * Sets the modifiers explicitly specified on this declaration (JLS2 API only).
	 * <p>
	 * Note that the final modifier is the only meaningful modifier for local
	 * variable declarations.
	 * </p>
	 *
	 * @param modifiers the given modifiers (bit-wise or of <code>Modifier</code> constants)
	 * @exception UnsupportedOperationException if this operation is used in
	 * an AST later than JLS2
	 * @see Modifier
	 * @deprecated In the JLS3 API, this method is replaced by
	 * {@link  #modifiers()} which contains a list of a <code>Modifier</code> nodes.
	 */
	public void setModifiers(int modifiers) {
		internalSetModifiers(modifiers);
	}

	/**
	 * Internal synonym for deprecated method. Used to avoid
	 * deprecation warnings.
	 * @since 3.1
	 */
	/*package*/ final void internalSetModifiers(int pmodifiers) {
	    supportedOnlyIn2();
		preValueChange(MODIFIERS_PROPERTY);
		this.modifierFlags = pmodifiers;
		postValueChange(MODIFIERS_PROPERTY);
	}

	/**
	 * Returns the base type declared in this variable declaration statement.
	 * <p>
	 * N.B. The individual child variable declaration fragments may specify
	 * additional array dimensions. So the type of the variable are not
	 * necessarily exactly this type.
	 * </p>
	 *
	 * @return the base type
	 */
	public Type getType() {
		if (this.baseType == null) {
			// lazy init must be thread-safe for readers
			synchronized (this) {
				if (this.baseType == null) {
					preLazyInit();
					this.baseType = postLazyInit(this.ast.newPrimitiveType(PrimitiveType.INT), TYPE_PROPERTY);
				}
			}
		}
		return this.baseType;
	}

	/**
	 * Sets the base type declared in this variable declaration statement to
	 * the given type.
	 *
	 * @param type the new base type
	 * @exception IllegalArgumentException if:
	 * <ul>
	 * <li>the node belongs to a different AST</li>
	 * <li>the node already has a parent</li>
	 * </ul>
	 */
	public void setType(Type type) {
		if (type == null) {
			throw new IllegalArgumentException();
		}
		ASTNode oldChild = this.baseType;
		preReplaceChild(oldChild, type, TYPE_PROPERTY);
		this.baseType = type;
		postReplaceChild(oldChild, type, TYPE_PROPERTY);
	}

	/**
	 * Returns the live list of variable declaration fragments in this statement.
	 * Adding and removing nodes from this list affects this node dynamically.
	 * All nodes in this list must be <code>VariableDeclarationFragment</code>s;
	 * attempts to add any other type of node will trigger an
	 * exception.
	 *
	 * @return the live list of variable declaration fragments in this
	 *    statement (element type: {@link VariableDeclarationFragment})
	 */
	public List fragments() {
		return this.variableDeclarationFragments;
	}

	@Override
	int memSize() {
		return super.memSize() + 4 * 4;
	}

	@Override
	int treeSize() {
		return
			memSize()
			+ (this.modifiers == null ? 0 : this.modifiers.listSize())
			+ (this.baseType == null ? 0 : getType().treeSize())
			+ this.variableDeclarationFragments.listSize();
	}
}

