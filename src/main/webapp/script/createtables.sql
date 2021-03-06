/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  avbravo
 * Created: 08/19/2021
 */

/**
*Grupo
*
*/


/**
*"NOTIFICACION
*Guarda la notificacion de cada evento se usara
para actualizar el dasboard mediabte un <p:idleMonitor
*
*/
CREATE SEQUENCE NOTIFICACION_SEQ MINVALUE 1 MAXVALUE 9999999999999999999999999999 START WITH 1  INCREMENT BY 1;
 CREATE TABLE NOTIFICACION 
   (	NOTIFICACIONID NUMBER DEFAULT NOTIFICACION_SEQ.nextval NOT NULL, 
	ID NUMBER NOT NULL, 
        TIPODID VARCHAR2(100 BYTE) NOT NULL,
        TRANSACCION VARCHAR2(250 BYTE) NOT NULL,        
        CONSTRAINT NOTIFICACIONPK PRIMARY KEY (NOTIFICACIONID)
  )  ;








/**
*"Historial"
*
*/
CREATE SEQUENCE HISTORIAL_SEQ MINVALUE 1 MAXVALUE 9999999999999999999999999999 START WITH 1  INCREMENT BY 1;
 CREATE TABLE HISTORIAL 
   (	HISTORIALID NUMBER DEFAULT HISTORIAL_SEQ.nextval NOT NULL, 
	TABLA VARCHAR2(100 BYTE) NOT NULL,  
        MODULO VARCHAR2(100 BYTE) NOT NULL,
        EVENTO VARCHAR2(100 BYTE) NOT NULL,
        CONTENIDO VARCHAR2(3000 BYTE) ,
	USUARIOID NUMBER NOT NULL,
        FECHA DATE NOT NULL,
        CONSTRAINT HISTORIALPK PRIMARY KEY (HISTORIALID)
  )  ;








/**
*BANCO
*control = si=> se usa solo para telered
*/

CREATE SEQUENCE BANCO_SEQ MINVALUE 1 MAXVALUE 9999999999999999999999999999 START WITH 1  INCREMENT BY 1;
 CREATE TABLE BANCO 
   (	 BANCOID NUMBER DEFAULT BANCO_SEQ.nextval NOT NULL, 
	 BANCO VARCHAR2(100 BYTE) NOT NULL,  
         SIGLAS VARCHAR2(35 BYTE) NOT NULL,
         ACTIVO VARCHAR2(2 BYTE) NOT NUll ,
         DESCRIPCION VARCHAR2(100 BYTE) NOT NULL,
 ESCONTROL VARCHAR2(2 BYTE) NOT NULL, 
	 ORDEN NUMBER NOT NULL,
        
         
        
         CONSTRAINT BANCOPK PRIMARY KEY (BANCOID),
         CONSTRAINT BANCOUNIQUE UNIQUE (BANCO)
  )  ;

/**
**CAJERP
*/
CREATE SEQUENCE CAJERO_SEQ MINVALUE 1 MAXVALUE 9999999999999999999999999999 START WITH 1  INCREMENT BY 1;
 CREATE TABLE CAJERO
   (	 CAJEROID NUMBER DEFAULT CAJERO_SEQ.nextval NOT NULL,  
	 CAJERO  VARCHAR2(10 BYTE) NOT NULL,  
	 BANCOID NUMBER NOT NULL,  
         ACTIVO VARCHAR2(2 BYTE) NOT NUll ,
         DESCRIPCION VARCHAR2(100 BYTE) NOT NULL,
         DIRECCION VARCHAR2(100 BYTE) NOT NULL,
         DIRECCIONCORTA  VARCHAR2(200 BYTE) NOT NULL,
         INFORMACIONADICIONAL  VARCHAR2(100 BYTE) NOT NULL,
	 ORDEN NUMBER NOT NULL,
            
         CONSTRAINT CAJEROPK PRIMARY KEY (CAJEROID),
         CONSTRAINT CAJEROUNIQUE UNIQUE (CAJERO,BANCOID),
CONSTRAINT CAJEROBANCOFK FOREIGN KEY (BANCOID)  REFERENCES BANCO(BANCOID)
  )  ;

/*
========================================================
*/

/**
*Grupo
*
*/
   
-- CREATE SEQUENCE GRUPO_SEQ MINVALUE 1 MAXVALUE 9999999999999999999999999999 START WITH 1  INCREMENT BY 1;
--  CREATE TABLE GRUPO 
--    (	GRUPOID NUMBER DEFAULT GRUPO_SEQ.nextval NOT NULL,
-- 	 GRUPO VARCHAR2(100 BYTE) NOT NULL,  
--          ACTIVO VARCHAR2(2 BYTE) NOT NUll ,  
--          DESCRIPCION VARCHAR2(100 BYTE) NOT NULL,      
-- 	 ORDEN NUMBER NOT NULL,
--          CONSTRAINT GRUPOPK PRIMARY KEY (GRUPOID)
--   )  ;

/*
Usuario
*/
CREATE SEQUENCE USUARIO_SEQ MINVALUE 1 MAXVALUE 9999999999999999999999999999 START WITH 1  INCREMENT BY 1;
CREATE TABLE USUARIO
( USUARIOID NUMBER DEFAULT USUARIO_SEQ.nextval NOT NULL,
  USERNAME  VARCHAR2(50 BYTE) NOT NULL,
  PASSWORD VARCHAR2(150 BYTE) NOT NULL,
  NOMBRE VARCHAR2(100 BYTE) NOT NULL,
  CEDULA VARCHAR2(35 BYTE) NOT NULL,
  EMAIL VARCHAR2(100 BYTE) NOT NULL,
  TELEFONO VARCHAR2(35 BYTE) NOT NULL,
  CELULAR VARCHAR2(35 BYTE) NOT NULL,
  ACTIVO VARCHAR2(2 BYTE) NOT NUll ,
  MODULOBANCO VARCHAR2(2 BYTE) NOT NUll ,
  MODULOTECNICO VARCHAR2(2 BYTE) NOT NUll ,
  MODULOCONTROLMANUAL VARCHAR2(2 BYTE) NOT NUll ,
  MODULOMANTENIMIENTO VARCHAR2(2 BYTE) NOT NUll ,
  BANCOID NUMBER NOT NULL,
  ORDEN NUMBER NOT NULL,
  CONSTRAINT USUARIOPK PRIMARY KEY (USUARIOID),
  CONSTRAINT USUARIOUNIQUE  UNIQUE (USERNAME),
  CONSTRAINT USUARIOBANCOFK FOREIGN KEY (BANCOID)  REFERENCES BANCO(BANCOID)
);


/**
GRUPOUSUARIO
**/
-- CREATE SEQUENCE GRUPOUSUARIO_SEQ MINVALUE 1 MAXVALUE 9999999999999999999999999999 START WITH 1  INCREMENT BY 1;
--  CREATE TABLE GRUPOUSUARIO 
--    (	GRUPOUSUARIOID NUMBER DEFAULT GRUPOUSUARIO_SEQ.nextval NOT NULL,
--       USUARIOID  NUMBER NOT NULL,
--         GRUPOID NUMBER NOT NULL,
--          ACTIVO VARCHAR2(2 BYTE) NOT NUll ,
--      
--   CONSTRAINT GRUPOUSUARIOPK PRIMARY KEY (GRUPOUSUARIOID),
--   CONSTRAINT GRUPOUSUARIOUNIQUE UNIQUE (USUARIOID, GRUPOID),
--  CONSTRAINT GRUPOUSUARIOUSUARIOFK FOREIGN KEY (USUARIOID)  REFERENCES USUARIO(USUARIOID),
--  CONSTRAINT GRUPOUSUARIOGRUPO FOREIGN KEY (GRUPOID)  REFERENCES GRUPO(GRUPOID)
-- 
--   )  ;




/*
GRUPOACCION
*/

CREATE SEQUENCE GRUPOACCION_SEQ MINVALUE 1 MAXVALUE 9999999999999999999999999999 START WITH 1  INCREMENT BY 1;
CREATE TABLE GRUPOACCION 
   (	GRUPOACCIONID NUMBER DEFAULT GRUPOACCION_SEQ.nextval NOT NULL, 
	GRUPOACCION VARCHAR2(100 BYTE) NOT NULL, 
	ACTIVO VARCHAR2(2 BYTE) NOT NUll ,
	ORDEN NUMBER NOT NULL, 
	   
  CONSTRAINT GRUPOACCIONPK PRIMARY KEY (GRUPOACCIONID),
CONSTRAINT GRUPOACCIONUNIQUE UNIQUE (GRUPOACCION)
   )  ;

/**
ACCION
**/

CREATE SEQUENCE ACCION_SEQ MINVALUE 1 MAXVALUE 9999999999999999999999999999 START WITH 1  INCREMENT BY 1;
CREATE TABLE ACCION
   (	
        ACCIONID  NUMBER DEFAULT ACCION_SEQ.nextval NOT NULL,  
	ACCION VARCHAR2(100 BYTE) NOT NULL, 
	ACTIVO VARCHAR2(2 BYTE) NOT NUll ,
	ORDEN NUMBER NOT NULL, 
        GRUPOACCIONID NUMBER NOT NULL, 
        PREDETERMINADO VARCHAR2(2 BYTE) NOT NUll ,
	   
  CONSTRAINT ACCIONPK PRIMARY KEY (ACCIONID),
CONSTRAINT ACCIONUNIQUE UNIQUE (ACCION),
CONSTRAINT ACCIONGRUPOACCIONFK FOREIGN KEY (GRUPOACCIONID)  REFERENCES GRUPOACCION(GRUPOACCIONID)
   )  ;


/**
ACCIONRECIENTE
Son las acciones que han ocurrido recientemente se usa para las notificaciones
permite filtrar rapidamente por bancoid, cajeroid, agendaid
**/

CREATE SEQUENCE ACCIONRECIENTE_SEQ MINVALUE 1 MAXVALUE 9999999999999999999999999999 START WITH 1  INCREMENT BY 1;
CREATE TABLE ACCIONRECIENTE
   (	
        ACCIONRECIENTEID  NUMBER DEFAULT ACCIONRECIENTE_SEQ.nextval NOT NULL,  
        ACCIONID NUMBER NOT NULL,
        ESTADOID NUMBER  NOT NULL,
	ESTADO VARCHAR2(100 BYTE), 
        BANCOID NUMBER NOT NULL,
        CAJEROID NUMBER NOT NULL,
        CAJERO VARCHAR2(10 BYTE) NOT NULL,
        MODULO VARCHAR2(2 BYTE) NOT NULL,
        AGENDAID NUMBER NOT NULL,
        FECHA DATE,
        FECHACREACION DATE,
        FECHAAGENDADA DATE,
        FECHAEJECUCION DATE,
	TITULO VARCHAR2(100 BYTE), 
	MENSAJE VARCHAR2(250 BYTE), 
	ACTIVO VARCHAR2(2 BYTE) NOT NUll ,
	AUTORIZADO VARCHAR2(2 BYTE) NOT NUll ,
	VISTOBANCO VARCHAR2(2 BYTE) NOT NUll, 
        VISTOTECNICO VARCHAR2(2 BYTE) NOT NUll, 
        	   
  CONSTRAINT ACCIONRECIENTEPK PRIMARY KEY (ACCIONRECIENTEID)

   )  ;

/*
=============================================
*/


/**
GRUPOESTADO
En espera de ejecucion

*/
CREATE SEQUENCE GRUPOESTADO_SEQ MINVALUE 1 MAXVALUE 9999999999999999999999999999 START WITH 1  INCREMENT BY 1;
CREATE TABLE GRUPOESTADO 
   (	GRUPOESTADOID NUMBER DEFAULT GRUPOESTADO_SEQ.nextval NOT NULL,
	GRUPOESTADO VARCHAR2(100 BYTE), 
	 ACTIVO VARCHAR2(2 BYTE) NOT NUll ,
	ORDEN NUMBER, 
  
	   
  CONSTRAINT GRUPOESTADOPK PRIMARY KEY (GRUPOESTADOID),
CONSTRAINT GRUPOESTADOUNIQUE UNIQUE (GRUPOESTADO)
   );
/**
ESTADO
En espera de ejecucion

*/
CREATE SEQUENCE ESTADO_SEQ MINVALUE 1 MAXVALUE 9999999999999999999999999999 START WITH 1  INCREMENT BY 1;
CREATE TABLE ESTADO 
   (	ESTADOID NUMBER DEFAULT ESTADO_SEQ.nextval NOT NULL,
	ESTADO VARCHAR2(100 BYTE), 
	 ACTIVO VARCHAR2(2 BYTE) NOT NUll ,
	 PREDETERMINADO VARCHAR2(2 BYTE) NOT NUll ,
	ORDEN NUMBER, 
	GRUPOESTADOID NOT NUll ,   
	   
  CONSTRAINT ESTADOPK PRIMARY KEY (ESTADOID),
CONSTRAINT ESTADOUNIQUE UNIQUE (ESTADO),
CONSTRAINT ESTADOGRUPOESTADOFK FOREIGN KEY (GRUPOESTADOID)  REFERENCES GRUPOESTADO(GRUPOESTADOID)
   );




/**
Meses
*/
CREATE SEQUENCE MES_SEQ MINVALUE 1 MAXVALUE 9999999999999999999999999999 START WITH 1  INCREMENT BY 1;
CREATE TABLE MES
   (	MESID NUMBER DEFAULT MES_SEQ.nextval NOT NULL, 
	MES VARCHAR2(100 BYTE), 
	 ACTIVO VARCHAR2(2 BYTE) NOT NUll ,
NUMERO NUMBER NOT NULL,
	ORDEN NUMBER, 
	   
 CONSTRAINT MESPK PRIMARY KEY (MESID),
CONSTRAINT MESUNIQUE UNIQUE (MES)
   ) ;






/**
EMAILCONFIGURATION
Para envio de notificaciones
*/

CREATE SEQUENCE EMAILCONFIGURATION_SEQ MINVALUE 1 MAXVALUE 9999999999999999999999999999 START WITH 1  INCREMENT BY 1;

 CREATE TABLE EMAILCONFIGURATION
   (	EMAILCONFIGURATIONID NUMBER DEFAULT EMAILCONFIGURATION_SEQ.nextval NOT NULL,
        EMAIL VARCHAR2(150 BYTE), 
       PASSWORD VARCHAR2(150 BYTE), 
 
MAILSMTPHOST VARCHAR2(100 BYTE),
 MAILSMTPAUTH VARCHAR2(15 BYTE),
 MAILSMTPPORT VARCHAR2(15 BYTE),
MAILSMTPSTARTTLSENABLE VARCHAR2(15 BYTE),
 ACTIVO VARCHAR2(2 BYTE) NOT NUll ,
	ORDEN NUMBER, 	   
 CONSTRAINT EMAILCONFIGURATIONPK PRIMARY KEY (EMAILCONFIGURATIONID),
CONSTRAINT EMAILCONFIGURATIONUNIQUE UNIQUE (EMAIL)

   );
    



/**
*TOKEN
*
*/
   
CREATE SEQUENCE TOKEN_SEQ MINVALUE 1 MAXVALUE 9999999999999999999999999999 START WITH 1  INCREMENT BY 1;
 CREATE TABLE TOKEN 
   (	TOKENID NUMBER DEFAULT TOKEN_SEQ.nextval NOT NULL,
	TOKEN VARCHAR2(4 BYTE) NOT NULL,  
        CODIGOTRANSACCION VARCHAR2(200 BYTE) NOT NULL,
         ACTIVO VARCHAR2(2 BYTE) NOT NUll ,  
         USADO VARCHAR2(2 BYTE) NOT NUll ,  
         VENCIDO VARCHAR2(2 BYTE) NOT NUll ,  
         USUARIOID NUMBER  NOT NULL,      
	 FECHAGENERACION DATE, 
         FECHAVENCIMIENTO DATE,
         CONSTRAINT TOKENPK PRIMARY KEY (TOKENID)
  )  ;

/**

=======================================================
*/
/**
*AGENDA
Lleva el control de todos los eventos para el cajeron
el campo CODIGOTRANSACCION  se usa para la generaci??n de agenda y permite localizar
la agenda correspondiente despues de su creacion
*
*/

CREATE SEQUENCE AGENDA_SEQ  MINVALUE 1 MAXVALUE 9999999999999999999999999999 START WITH 1  INCREMENT BY 1;

 CREATE TABLE AGENDA
   (	
          AGENDAID NUMBER DEFAULT AGENDA_SEQ.nextval NOT NULL,
          BANCOID NUMBER NOT NULL,
          CAJEROID NUMBER NOT NULL,
          CAJERO VARCHAR2(10 BYTE) NOT NULL,       
          ESTADOID NUMBER NOT NULL,
          ACCIONID NUMBER NOT NULL,
          FECHA DATE,
          FECHAAGENDADA DATE,
          FECHAEJECUCION DATE,
          USUARIOIDSOLICITA NUMBER NOT NULL,
          USUARIOIDATIENDE  NUMBER NOT NULL,
          CODIGOTRANSACCION VARCHAR2(200 BYTE) NOT NULL,
          ACTIVO VARCHAR2(2 BYTE) NOT NUll ,  
         CONSTRAINT AGENDAPK PRIMARY KEY (AGENDAID)
  )  ;

/*
AGENDA HISTORIAL
*/   
CREATE SEQUENCE AGENDAHISTORIAL_SEQ MINVALUE 1 MAXVALUE 9999999999999999999999999999 START WITH 1  INCREMENT BY 1;
CREATE TABLE  AGENDAHISTORIAL
   (

       AGENDAHISTORIALID NUMBER DEFAULT AGENDAHISTORIAL_SEQ.nextval NOT NULL,
          AGENDAID NUMBER NOT NULL,
          BANCOID NUMBER NOT NULL,
          CAJEROID NUMBER NOT NULL,
         CAJERO VARCHAR2(10 BYTE) NOT NULL,   
          ESTADOID NUMBER NOT NULL,
          ACCIONID NUMBER NOT NULL,
          FECHA DATE,
          FECHAAGENDADA DATE,
FECHAEJECUCION DATE,
          USUARIOIDSOLICITA NUMBER NOT NULL,
          USUARIOIDATIENDE  NUMBER NOT NULL,
          ACTIVO VARCHAR2(2 BYTE) NOT NUll ,  
 EVENTOOCURRIDO VARCHAR2(200 BYTE) NOT NULL, 
FECHAEVENTO DATE,
USUARIOEVENTO NUMBER NOT NULL,
        CONSTRAINT AGENDAHISTORIALPK PRIMARY KEY (AGENDAHISTORIALID)

  )  ;


/**
PARAMETROS
**/

CREATE SEQUENCE PARAMETRO_SEQ MINVALUE 1 MAXVALUE 9999999999999999999999999999 START WITH 1  INCREMENT BY 1;
CREATE TABLE PARAMETRO
   (	
        PARAMETROID  NUMBER DEFAULT PARAMETRO_SEQ.nextval NOT NULL,  
	CLAVE VARCHAR2(100 BYTE) NOT NULL, 
	VALOR VARCHAR2(100 BYTE) NOT NUll ,
  CONSTRAINT PARAMETROPK PRIMARY KEY (PARAMETROID),
CONSTRAINT PARAMETROSUNIQUE UNIQUE (CLAVE)
   )  ;
