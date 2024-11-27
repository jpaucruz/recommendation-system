# Usa una imagen de Node.js para compilar el proyecto Angular
FROM node:18 AS build

# Establece el directorio de trabajo
WORKDIR /app

# Copia los archivos package.json y package-lock.json para instalar las dependencias
COPY package*.json ./

# Instala las dependencias de Node
RUN npm install

# Copia el resto del código del proyecto
COPY . .

# Compila la aplicación Angular
RUN npm run build --prod

# Usa una imagen base de Nginx para servir la aplicación
FROM nginx:alpine

# Copia los archivos de la compilación (desde la etapa anterior) al servidor web de Nginx
COPY --from=build /app/dist/website/browser /usr/share/nginx/html

# Expone el puerto 80 para Nginx
EXPOSE 80

# Inicia Nginx
CMD ["nginx", "-g", "daemon off;"]
