<?xml version='1.0' encoding='UTF-8' ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"
      xmlns:h="http://xmlns.jcp.org/jsf/html"
      xmlns:composite="http://java.sun.com/jsf/composite"
      xmlns:p="http://primefaces.org/ui"
      xmlns:jsf="http://xmlns.jcp.org/jsf">
    <composite:interface >
        <composite:attribute name="action" />
    </composite:interface>
    <composite:implementation>

        <!-- Diseño del sidebar -->
        <nav class="sidebar sidebar-offcanvas" id="sidebar">
            <ul class="nav">
                <li class="nav-item">         
                    <p:commandLink class="nav-link" action="#{navigatorContoller.go('dashboard.xhtml')}" >
                        <i class="ti-shield menu-icon"></i>
                        <span class="menu-title">Dashboard</span>
                    </p:commandLink>
                </li>
                <li class="nav-item"> 
                    <p:commandLink class="nav-link"  action="#{navigatorContoller.go('buscarcajero.xhtml')}" >
                        <i class="ti-search menu-icon"/>
                        <span class="menu-title">Buscar</span>
                    </p:commandLink>
                </li>
                <li class="nav-item"> 
                    <p:commandLink class="nav-link" action="#{navigatorContoller.go('agendados.xhtml')}" >
                        <i class="menu-icon ti-calendar"></i>
                        <span class="menu-title">Agendados</span>
                    </p:commandLink>
                </li>
                <li class="nav-item"> 
                    <p:commandLink class="nav-link" action="#{navigatorContoller.go('totalestadobanco.xhtml')}" >
                        <i class="menu-icon ti-agenda"></i>
                        <span class="menu-title">Totales por banco</span>
                    </p:commandLink>
                </li>
            </ul>
        </nav>

    </composite:implementation>
</html>