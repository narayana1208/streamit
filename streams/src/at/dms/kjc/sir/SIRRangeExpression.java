package at.dms.kjc.sir;

import at.dms.kjc.*;
import at.dms.compiler.*;

/**
 * This represents a range [min,average,max], e.g., [1,2,3] or [1,*,*]
 * as used in the declaration of an I/O rate.
 */
public class SIRRangeExpression extends JExpression {
    /**
     * The minimum of the range.
     */
    private JExpression min;
    /**
     * The average of the range.  If no average was specified, this
     * will be equal to a SIRDynamicToken.
     */
    private JExpression ave;
    /**
     * The maximum of the range.
     */
    private JExpression max;

    private SIRRangeExpression() {
        // for cloner only
    }

    public SIRRangeExpression(JExpression min, JExpression ave, JExpression max) {
        this.min = min;
        this.ave = ave;
        this.max = max;
    }

    /**
     * Returns minimum of the range.
     */ 
    public JExpression getMin() {
        return min;
    }

    /**
     * Returns average of the range.
     */ 
    public JExpression getAve() {
        return ave;
    }

    /**
     * Returns maximum of the range.
     */ 
    public JExpression getMax() {
        return max;
    }

    /**
     * Sets minimum of the range.
     */ 
    public void setMin(JExpression min) {
        this.min = min;
    }

    /**
     * Sets average of the range.
     */ 
    public void setAve(JExpression ave) {
        this.ave = ave;
    }

    /**
     * Sets maximum of the range.
     */ 
    public void setMax(JExpression max) {
        this.max = max;
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
            ((SLIRVisitor)p).visitRangeExpression(this);
        } else {
            // visit the children
            min.accept(p);
            ave.accept(p);
            max.accept(p);
        }
    }

    /**
     * Accepts the specified attribute visitor.
     * @param   p               the visitor
     */
    public Object accept(AttributeVisitor p) {
        if (p instanceof SLIRAttributeVisitor) {
            return ((SLIRAttributeVisitor)p).visitRangeExpression(this);
        } else {
            return this;
        }
    }

    /**
     * Returns string representation as range, e.g, [1,2,3]
     */
    public String toString() {
        // strings for min, ave, max
        String minStr,aveStr,maxStr;
        // take int value, otherwise IR toString
        if (min instanceof JIntLiteral) {
            minStr = ""+min.intValue();
        }  else {
            minStr = min.toString();
        }
        if (ave instanceof JIntLiteral) {
            aveStr = ""+ave.intValue();
        }  else {
            aveStr = ave.toString();
        }
        if (max instanceof JIntLiteral) {
            maxStr = ""+max.intValue();
        }  else {
            maxStr = max.toString();
        }
        return "[" + minStr + "," + aveStr + "," + maxStr + "]";
    }

    // ----------------------------------------------------------------------
    // Meaningful interface from JExpression
    // ----------------------------------------------------------------------
    /**
     * Returns true only for SIRRangeExpressions, which represent a
     * dynamic range of values.
     */
    public boolean isDynamic() {
        return true;
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
        at.dms.kjc.sir.SIRRangeExpression other = new at.dms.kjc.sir.SIRRangeExpression();
        at.dms.kjc.AutoCloner.register(this, other);
        deepCloneInto(other);
        return other;
    }

    /** Clones all fields of this into <other> */
    protected void deepCloneInto(at.dms.kjc.sir.SIRRangeExpression other) {
        super.deepCloneInto(other);
        other.min = (at.dms.kjc.JExpression)at.dms.kjc.AutoCloner.cloneToplevel(this.min);
        other.ave = (at.dms.kjc.JExpression)at.dms.kjc.AutoCloner.cloneToplevel(this.ave);
        other.max = (at.dms.kjc.JExpression)at.dms.kjc.AutoCloner.cloneToplevel(this.max);
    }

    /** THE PRECEDING SECTION IS AUTO-GENERATED CLONING CODE - DO NOT MODIFY! */
}
