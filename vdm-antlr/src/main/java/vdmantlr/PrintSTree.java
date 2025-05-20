package vdmantlr;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import vdmantlr.generated.VDMBaseListener;
import vdmantlr.generated.VDMParser;
import vdmantlr.generated.VDMLexer;


import org.antlr.v4.gui.TestRig;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonToken;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.DiagnosticErrorListener;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.atn.PredictionMode;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.ParseTreeProperty;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.lang.reflect.Method;


import com.fujitsu.vdmj.Settings;

public class PrintSTree {
  public static void main(String[] args) {

    String rule_name = "sl_document";

    if (args.length == 2) {
      rule_name = args[1];
    }

    if (args.length != 2 && args.length != 1) {
      System.out.println("Command takes the filename and an optional startrule");
      return;
    }

    try {
      VDMBaseListener listener = new VDMBaseListener();
      CharStream input = CharStreams.fromFileName(args[0], Settings.filecharset);
      VDMLexer lexer = new VDMLexer(input);
      CommonTokenStream tokens = new CommonTokenStream(lexer);
      VDMParser parser = new VDMParser(tokens);
      Method rule = parser.getClass().getMethod(rule_name);
      ParseTree t = (ParseTree)rule.invoke(parser);
      ParseTreeWalker.DEFAULT.walk(listener, t);
      System.out.println(t.toStringTree(parser));
    } catch (Throwable e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    }
  }
}
