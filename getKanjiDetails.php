<?php
    require_once '../unirest-php/src/Unirest.php';
    $File = "APIKey.txt";
    $MashapeKey = file_get_contents($File);
    $character = urlencode($_GET['character']);
    $response = Unirest\Request::get("https://kanjialive-api.p.mashape.com/api/public/kanji/" . $character,
      array(
        "X-Mashape-Key" => $MashapeKey,
        "Accept" => "application/json"
      )
    );
    
      echo $response->raw_body;
?>