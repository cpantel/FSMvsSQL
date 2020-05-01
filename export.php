<?php

$conn = new SQLite3('actividades.db3');


// ejecutar si es la primera vez
// $conn->exec('CREATE TABLE actividades (ts TEXT, type TEXT, source TEXT, destination TEXT, data TEXT);');

$insert = $conn->prepare('INSERT INTO actividades (ts, type, source, destination, data) VALUES (:ts, :type, :source, :destination, :data)');


while( $line = fgets(STDIN) ){
  if ( substr($line,0,1) != '#'  ) {
    $record = preg_split('/ /', $line);
    var_dump($record);
    $insert->bindValue('ts',          $record[0] . ' ' . $record[1]);
    $insert->bindValue('type',        $record[2]);
    $insert->bindValue('source',      $record[3]);
    $insert->bindValue('destination', $record[4]);
    $insert->bindValue('data',        $record[5]);
    $insert->execute();
  }

}
