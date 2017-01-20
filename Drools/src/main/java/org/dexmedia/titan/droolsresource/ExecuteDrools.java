package org.dexmedia.titan.droolsresource;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.ParseException;

import org.codehaus.jettison.json.JSONObject;
import org.dexmedia.titan.validation.ValidationUtility;
import org.drools.compiler.compiler.DroolsParserException;
import org.drools.compiler.compiler.PackageBuilder;
import org.drools.core.RuleBase;
import org.drools.core.RuleBaseFactory;
import org.drools.core.WorkingMemory;



//org.drools.util.DroolsStreamUtils....

public class ExecuteDrools {
	public void execute(JSONObject input, ValidationUtility ValidationUtility)
			throws DroolsParserException, IOException, ParseException {

		PackageBuilder packageBuilder = new PackageBuilder();
		String ruleFile = "/org/rules/RuleDemo.drl";
		InputStream resourceAsStream = getClass().getResourceAsStream(ruleFile);

		Reader reader = new InputStreamReader(resourceAsStream);
		packageBuilder.addPackageFromDrl(reader);
		org.drools.core.rule.Package rulesPackage = packageBuilder.getPackage();
		RuleBase ruleBase = RuleBaseFactory.newRuleBase();
		ruleBase.addPackage(rulesPackage);

		WorkingMemory workingMemory = ruleBase.newStatefulSession();
		workingMemory.insert(input);
		workingMemory.insert(ValidationUtility);
		workingMemory.fireAllRules();
		workingMemory.dispose();
	}
}
