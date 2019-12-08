import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DataService {

  constructor(private http: HttpClient) {
  }

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };
  getArtisans() {
    return this.http.get('http://localhost:8080/api/artisans');
  }

  getArtisan(id) {
    return this.http.get('http://localhost:8080/api/artisan?id=' + id);
  }

  SaveImg(filelist) {
    let file: File = filelist[0];
    let formData: FormData = new FormData();
    formData.append('uploadFile', file, file.name);
    let headers = new HttpHeaders();
    headers.append('Content-Type', 'multipart/form-data');
    headers.append('Accept', 'application/json');
    //  let options = new RequestOptions({ headers: headers });
    const options = {
      params: new HttpParams(),
      headers:  headers
    };
    this.http.post(`http://localhost:8080/api/files`, formData, options)

      .subscribe(
        data => console.log('success'),
        error => console.log(error)
      );
  }
  isRated(artisan, client) {
    let params = new HttpParams();
    params = params.append('artisan', artisan);
    params = params.append('client', client);
    console.log(params);
    return this.http.get<number>('http://localhost:8080/api/israted', {params});
  }

  rate(info): Observable<string> {
    return this.http.post<string>('http://localhost:8080/api/rate', info);
  }
}
