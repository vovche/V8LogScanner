package org.v8LogScanner.cmdScanner;

import org.v8LogScanner.cmdAppl.CmdCommand;
import org.v8LogScanner.commonly.Strokes;
import org.v8LogScanner.logsCfg.LogEvent;
import org.v8LogScanner.rgx.RegExp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CmdAddEventToCfg implements CmdCommand{

  @Override
  public String getTip() {
    return "";
  }

  @Override
  public void execute() {
    
    V8LogScannerAppl appl = V8LogScannerAppl.instance();

    // Input prop for existing event or for the new <Event></Event>
    List<LogEvent> events = appl.logBuilder.getLogEvents();
    LogEvent logEvent = null;
    if (events.size() > 0 ) {
      String[] choices = {"Add new", "Use existing"};
      String choicesRes = appl.getConsole().askInputFromList("Do you want to add new <events> tag or use existing?", choices);
      if (choicesRes == null) {
        return;
      } else if (Integer.parseInt(choicesRes) == 0) {
        logEvent = new LogEvent();
      } else {
          String eventIndex = appl.getConsole().askInputFromList("Input index of a prop from the list", events);
          if (eventIndex == null)
            return;
          logEvent = events.get(Integer.parseInt(eventIndex));
      }
    } else {
      logEvent = new LogEvent();
    }

    // Input prop <eq></eq>
    String propTypeIndex =  appl.getConsole().askInputFromList(
      "Input index of a prop from the list",
      RegExp.PropTypes.values()
    );

    if(propTypeIndex == null)
      return;
    //
    String [] textProp = {"input value of a prop:"};
    String valProp =  appl.getConsole().askInput(textProp, (text) -> !text.isEmpty(), true);

    if(valProp == null)
      return;

    String compTypeIndex =  appl.getConsole().askInputFromList(
      "Input index of a comparison type from the list",
      LogEvent.LogEventComparisons.values()
    );

    if(compTypeIndex == null)
      return;

    RegExp.PropTypes propType = RegExp.PropTypes.values()[Integer.parseInt(propTypeIndex)];

    logEvent.setProp(propType);
    logEvent.setVal(propType, valProp);
    logEvent.setComparison(propType, LogEvent.LogEventComparisons.values()[Integer.parseInt(compTypeIndex)]);
  }
}