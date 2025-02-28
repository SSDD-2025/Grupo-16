# Name of the Application TBD

## Participants of Group 16

<table>
  <thead>
    <th>Full Name</th>
    <th>Degree</th>
    <th>University Email</th>
    <th>Github Account</th>
  </thead>
  <tbody>
    <tr>
      <td>Arminda García Moreno</td>
      <td>GII + MAT</td>
      <td>a.garciamore.2022@alumnos.urjc.es</td>
      <td>armiiin-13</td>
    </tr>
    <tr>
      <td>Alfonso Rodríguez Gutt</td>
      <td>GII</td>
      <td>a.rodriguezgu.2022@alumnos.urjc.es</td>
      <td>AlfonsoRodr</td>
    </tr>
  </tbody>
</table>

## Execution Instructions
<strong><em>AQUÍ HABRÍA QUE INDICAR LOS PASOS QUE HAY QUE SEGUIR PARA DESCARGARSE EL REPOSITORIO DESDE GITHUB, COMO EJECUTAR LA APLICACIÓN Y LAS VERSIONES NECEESARIAS. </em></strong>

<strong><em>INDICAR TAMBIÉN EL USUARIO Y CONTRASEÑA DEL USUARIO ADMINISTRADOR. </em></strong>

## Entities Information
Next, all the entities that are part of the application will be shown, as well as the relationships between them.

### UserEntity
<table>
  <thead>
    <th>Related Entity</th>
    <th>Cardinality</th>
    <th>Annotation</th>
  </thead>
  <tbody>
    <tr>
      <td>Artist</td>
      <td>1..N</td>
      <td>@OneToMany</td>
    </tr>
    <tr>
      <td>Ticket</td>
      <td>1..N</td>
      <td>@OneToMany</td>
    </tr>
  </tbody>
</table>

### Artist
<table>
  <thead>
    <th>Related Entity</th>
    <th>Cardinality</th>
    <th>Annotation</th>
  </thead>
  <tbody>
    <tr>
      <td>TBD</td>
      <td>TBD</td>
      <td>TBD</td>
    </tr>
    <tr>
      <td>TBD</td>
      <td>TBD</td>
      <td>TBD</td>
    </tr>
  </tbody>
</table>

### Concert
<table>
  <thead>
    <th>Related Entity</th>
    <th>Cardinality</th>
    <th>Annotation</th>
  </thead>
  <tbody>
    <tr>
      <td>TBD</td>
      <td>TBD</td>
      <td>TBD</td>
    </tr>
    <tr>
      <td>TBD</td>
      <td>TBD</td>
      <td>TBD</td>
    </tr>
  </tbody>
</table>

### Ticket
<table>
  <thead>
    <th>Entity</th>
    <th></th>
  </thead>
  <tbody>
    <tr>
      <td>UserEntity</td>
      <td>N..1</td>
      <td>@ManyToOne</td>
    </tr>
    <tr>
      <td>Concert</td>
      <td>N..1</td>
      <td>@ManyToOne</td>
    </tr>
  </tbody>
</table>


To provide better clarity when viewing these relationships, the relational diagram of the application is shown below.

<strong><em>INSERTAR FOTO DEL DIAGRAMA RELACIONAL AQUÍ </em></strong>

## User Privileges
<strong><em>EN ESTA SECCIÓN HAY QUE EXPLICAR BREVEMENTE LOS PERMISOS DE CADA TIPO DE USUARIO. TAMBIÉN HAY QUE INDICAR DE QUÉ ENTIDADES ES DUEÑO CADA USUARIO </em></strong>

## Image Management
<strong><em>EN ESTA SECCIÓN HAY QUE INDICAR QUÉ ENTIDADES TIENEN ASOCIADAS UNA O VARIAS IMÁGENES POR CADA OBJETO/REGISTRO </em></strong>

## Application Functionality Overview
<strong><em>AQUÍ SE EXPLICARÁ A GROSSO MODO LA FUNCIONALIDAD DE LA APLICACIÓN </em> </strong>
<strong><em> AQUÍ SE INSERTARÍA EL DIAGRAMA DE NAVEGACIÓN Y EL DIAGRAMA DE CLASES/TEMPLATES </em></strong>

## Team Members Participation
In this section, each of the participants in the development of the application will explain the tasks they have been responsible for, showing their most notable commits, and those files on which they worked the most.

<strong><em>EN ESTA SECCIÓN SE INSERTARÍA EL LINK PARA EL TRELLO. SE IRÁ VIENDO SI AL INICIO (JUSTO AQUÍ) O AL FINAL, ES DECIR, DESPUÉS DE QUE CADA UNO HAYA TERMINADO DE EXPLICAR SU PARTE. </em></strong>

## Alfonso Rodríguez Gutt

## Arminda García Moreno

## David Rísquez Gómez
