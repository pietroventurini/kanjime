<?php
    require_once '../unirest-php/src/Unirest.php';
    $File = "KEYS/APIKey.txt";
    $MashapeKey = file_get_contents($File);
    $keyword = $_GET['keyword'];
    $response = Unirest\Request::get("https://kanjialive-api.p.mashape.com/api/public/search/" . strtolower($keyword),
      array(
        "X-Mashape-Key" => $MashapeKey,
        "Accept" => "application/json"
      )
    );

      echo $response->raw_body;
?>