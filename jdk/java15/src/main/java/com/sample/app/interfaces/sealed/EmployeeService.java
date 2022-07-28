package com.sample.app.interfaces.sealed;

public sealed interface EmployeeService permits PermanentEmployeeServiceImpl,TemporaryEmployeeServiceImpl {

}
