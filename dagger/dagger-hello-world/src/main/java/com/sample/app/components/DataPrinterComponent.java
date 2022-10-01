package com.sample.app.components;

import com.sample.app.modules.DependenciesProviderModule;
import com.sample.app.util.DataPrinter;

import dagger.Component;

@Component(modules = {DependenciesProviderModule.class})
public interface DataPrinterComponent {
	
	DataPrinter dataPrinter();

}
