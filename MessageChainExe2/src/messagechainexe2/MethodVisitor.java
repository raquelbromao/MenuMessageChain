package messagechainexe2;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.MethodDeclaration;

/**
 * 
 * @author Romao
 * @date 29/07/16
 * @time 14:34
 * 
 * To get information about the AST you can use the Visitor Pattern.
 * This allows you to add a visitor to the AST for a specific element. 
 * In this visitor can you capture information about the object and return it after processing the AST.
 * Created for this the following class.
 *
 */

public class MethodVisitor extends ASTVisitor {
        List<MethodDeclaration> methods = new ArrayList<MethodDeclaration>();

        @Override
        public boolean visit(MethodDeclaration node) {
                methods.add(node);
                return super.visit(node);
        }

        public List<MethodDeclaration> getMethods() {
                return methods;
        }
}