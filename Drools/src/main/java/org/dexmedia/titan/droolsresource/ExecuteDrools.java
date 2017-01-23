package org.dexmedia.titan.droolsresource;

import java.io.IOException;
import java.text.ParseException;

import org.codehaus.jettison.json.JSONObject;
import org.dexmedia.titan.validation.Validation;
import org.dexmedia.titan.validation.ValidationUtility;
import org.drools.compiler.compiler.DroolsParserException;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

public class ExecuteDrools {
	public void execute(JSONObject input, ValidationUtility ValidationUtility)
			throws DroolsParserException, IOException, ParseException {

		KieServices ks = KieServices.Factory.get();
		KieContainer kContainer = ks.getKieClasspathContainer();
		KieSession ksession = kContainer.newKieSession("ksession-rules");
		System.out.println(ksession);

		Validation validation = new Validation();
		ksession.insert(input);
		ksession.insert(ValidationUtility);
		ksession.insert(validation);
		ksession.fireAllRules();
		ksession.dispose();
	}
}
