import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Project } from '../models/project';
import { MatGridTileHeaderCssMatStyler } from '@angular/material';

@Injectable({
  providedIn: 'root'
})
export class ArtisanService {
  httpHeaders = new HttpHeaders().set('Content-Type', 'application/x-www-form-urlencoded');
  options = {
    headers: this.httpHeaders
  };
  constructor(private httpClient: HttpClient) { }

  getProjectList(id) {
    return this.httpClient.get('http://localhost:8080/api/artisan/projects?id=' + id);
  }
  acceptOffer(idProject, cost, currency) {
    return this.httpClient.post('http://localhost:8080/api/artisan/project/offer?idProject=' + idProject + "&cost=" + cost + "&currency=" + currency, null, this.options);
  }

  declineOffer(idProject) {
    return this.httpClient.post('http://localhost:8080/api/artisan/project/decline?idProject=' + idProject, null, this.options);
  }
}