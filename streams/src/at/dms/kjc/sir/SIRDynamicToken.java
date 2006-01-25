package at.dms.kjc.sir;

import at.dms.kjc.*;
import at.dms.compiler.*;

/**
 * This represents the dynmic token '*' as in an I/O rate declaration:
 * [2,*,*].  Note that this currently appears ONLY inside of a range
 * expression.  If a rate is declared as a plain '*', then it will be
 * converted (by the frontend) into [*,*,*].
 */
public class SIRDynamicToken extends JExpression {

    public SIRDynamicToken() {
        super();
    }

    /**
     * Ranges are for integers.
     */
    public CType getType() {
        return CStdType.Integer;
    }

    /**
     * Accepts the specified visitor.
     */
    public void accept(KjcVisitor p) {
        if (p instanceof SLIRVisitor) {
            ((SLIRVisitor)p).visitDynamicToken(this);
        }
    }

    /**
     * Accepts the specified attribute visitor.
     * @param   p               the visitor
     */
    public Object accept(AttributeVisitor p) {
        if (p instanceof SLIRAttributeVisitor) {
            return ((SLIRAttributeVisitor)p).visitDynamicToken(this);
        } else {
            return this;
        }
    }

    public String toString() {
        return "*";
    }

    // ----------------------------------------------------------------------
    // Meaningless leftover interface from JExpression
    // ----------------------------------------------------------------------
    /**
     * Throws an exception (NOT SUPPORTED YET)
     */
    public JExpression analyse(CExpressionContext context) throws PositionedError {
        at.dms.util.Utils.fail("Analysis of custom nodes not supported yet.");
        return this;
    }
    /**
     * Generates JVM bytecode to evaluate this expression.  NOT SUPPORTED YET.
     *
     * @param   code        the bytecode sequence
     * @param   discardValue    discard the result of the evaluation ?
     */
    public void genCode(CodeSequence code, boolean discardValue) {
        at.dms.util.Utils.fail("Visitors to custom nodes not supported yet.");
    }
    // ----------------------------------------------------------------------


    /** THE FOLLOWING SECTION IS AUTO-GENERATED CLONING CODE - DO NOT MODIFY! */

    /** Returns a deep clone of this object. */
    public Object deepClone() {
        at.dms.kjc.sir.SIRDynamicToken other = new at.dms.kjc.sir.SIRDynamicToken();
        at.dms.kjc.AutoCloner.register(this, other);
        deepCloneInto(other);
        return other;
    }

    /** Clones all fields of this into <other> */
    protected void deepCloneInto(at.dms.kjc.sir.SIRDynamicToken other) {
        super.deepCloneInto(other);
    }

    /** THE PRECEDING SECTION IS AUTO-GENERATED CLONING CODE - DO NOT MODIFY! */
}
